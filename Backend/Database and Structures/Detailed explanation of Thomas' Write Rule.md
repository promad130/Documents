## 1) Recall basic Timestamp Ordering (TO)

Each transaction gets a unique timestamp `TS(T)` (smaller = older).

For each data item `Q`, the system tracks:

- `R_TS(Q)`: largest timestamp of any transaction that successfully **read** `Q`
- `W_TS(Q)`: largest timestamp of any transaction that successfully **wrote** `Q`

### In basic TO, when `T` wants to write `Q`:

- If `TS(T) < R_TS(Q)`: some _newer_ transaction already read the old value that `T` would overwrite → **abort T** (rollback).
- Else if `TS(T) < W_TS(Q)`: some _newer_ transaction already wrote `Q` → basic TO would also **abort T** (“write is too old”).
- Else: allow the write and set `W_TS(Q) = TS(T)`.

That second abort case is what Thomas’ Write Rule changes.

## 2) Thomas’ Write Rule: “If the write is too old, ignore it”

Under TWR, when `T` tries `write(Q)` and finds:

- `TS(T) < W_TS(Q)` (i.e., **Q has already been written by a newer transaction**)

then **do not abort** `T`. Instead, **ignore** `T`’s write because it is **obsolete**.

### Why is it safe to ignore?

If a newer transaction has already written `Q`, then in any timestamp-respecting serial order, that newer transaction would come **after** `T` and its write would be the one that “wins” in the final database state.

So letting `T` overwrite `Q` now would only be undone later anyway. Ignoring `T`’s write directly achieves the same final effect **without forcing a rollback**.

Important: this “ignore” is only safe for the **`TS(T) < W_TS(Q)`** case, not for **`TS(T) < R_TS(Q)`**.

## 3) Concrete example (why abort vs ignore)

Assume `TS(T1)=1` (older), `TS(T2)=2` (newer).

Schedule:

1. `T2: write(Q)` → sets `W_TS(Q)=2`
2. `T1: write(Q)` (older write arrives late)

- Basic TO: sees `TS(T1)=1 < W_TS(Q)=2` → **abort T1**
- Thomas’ rule: sees the same condition → **ignore `T1`’s write** (no abort)

Final value of `Q` remains as written by `T2`, which matches what you’d get if you serialized by timestamps (`T1` then `T2`).

## 4) What you gain: fewer rollbacks, more concurrency

Basic TO aborts transactions for obsolete writes, which can be frequent in high-contention workloads. TWR converts many of those aborts into cheap “no-op” writes → **higher throughput**.

## 5) “Allows view-serializable schedules” (what that means here)

Basic TO aims for **conflict-serializable** schedules (stricter). TWR can accept some schedules that are **not conflict-serializable** but are still **view-serializable** because:

- the obsolete write that would create a conflict is simply ignored,
- and the resulting _effects_ (who reads what, and what the final writes are) still match some serial execution.

So TWR relaxes the rule “every conflicting write forces an abort” into “some conflicts are harmless because the write wouldn’t matter in the final view.”

## 6) Key caveat (very important)

Thomas’ Write Rule applies **only** when the write is “too old” due to `W_TS(Q)`:

- If `TS(T) < R_TS(Q)`, you still **must abort** in timestamp protocols, because a newer transaction has already _read_ a value that would become inconsistent if the older write were allowed.

# Doubt: ok, but if we do thomas' write rule, wont this cause issues in the logic of the backend? like the whole database isn't updated properly as we ignored the values? for example adding money to the bank account

Yes — **Thomas’ Write Rule can absolutely break application logic like “add money”** if you model it as a blind write or if you assume “every write request must take effect.” The point is:

- TWR is safe only with respect to **serializability at the database level** (specifically it can allow **view-serializable** schedules),
- but your *business operation* (deposit/transfer) is usually **not** a simple “set balance = v” overwrite; it’s an **increment** that must not be lost.

### 1) What “ignoring a write” really means
Ignoring a write happens when the DB detects it’s **obsolete**:

`TS(T) < W_TS(Q)` → a newer transaction already wrote `Q`, so the older write would not be the final value in any timestamp-ordered serial execution anyway.

So the DB is saying: “If I let you write, I’d have to overwrite a value that must later be overwritten again by the newer transaction to match serialization. So I’ll skip your write.”

That’s fine for some workloads, but it can be surprising for application developers if they think in terms of “I issued an UPDATE, so it must apply.”

### 2) Bank deposit example: where it goes wrong (lost update)
Suppose `balance = 100`.

Two deposits happen concurrently:
- `T1`: deposit +10
- `T2`: deposit +20

If implemented as **read-modify-write**:

- `T1` reads 100, computes 110
- `T2` reads 100, computes 120
- `T2` writes 120 first (newer)
- `T1` tries to write 110 later (older)

With **Thomas’ Write Rule**, `T1`’s write can be “too old” and get **ignored**, leaving balance at **120**.

But the correct result of both deposits is **130**.

So yes: from the business perspective, you “lost” the +10 deposit.

### 3) Important nuance: it’s not “database not updated properly”
It *is* updated “properly” according to the concurrency-control guarantee it chose (view-serializability / timestamp rules). The problem is that **your operation isn’t safe under that isolation/concurrency model**.

Deposits are not naturally “last-write-wins.” They are **commutative increments** and must be protected from lost updates.

### 4) How real systems avoid this
To make “add money” safe, you usually do one of these:

**A) Atomic update (no read-modify-write):**
```sql
UPDATE accounts
SET balance = balance + 10
WHERE id = 1;
```
This is treated as an atomic operation by the DB engine (with locking/MVCC rules), so it won’t be dropped as an “obsolete overwrite” in the same way.

**B) Stronger isolation / locking**
Use row locks / 2PL, or an isolation level that prevents lost updates (e.g., `SERIALIZABLE`, or `SELECT ... FOR UPDATE` pattern).

**C) Ledger/event sourcing**
Instead of overwriting balance, insert transactions:
```sql
INSERT INTO ledger(account_id, amount) VALUES (1, +10);
```
and compute balance as sum (or maintain it with safe mechanisms). This avoids “ignored overwrites” entirely.

### 5) When Thomas’ Write Rule is actually OK
TWR is fine when:
- the write is an **idempotent set** (e.g., setting a cached value),
- or “last writer wins” is acceptable,
- or the older write is truly irrelevant (e.g., writing a derived value that will be superseded).

It is **not OK** for:
- increments, counters, inventory decrement,
- bank transfers,
- any invariant-preserving update where every operation must be reflected.

So your intuition is correct: **ignoring writes can violate backend logic** unless your operations are designed/implemented in a way that’s safe under that concurrency control.