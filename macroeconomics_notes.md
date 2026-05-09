# Macroeconomics Master Notes  
## Formulas with Derivations + Theory + Examples + Case Studies

---

## Notation and Conventions
- \(e\): nominal exchange rate in **direct quotation** (domestic currency per unit of foreign currency)
- \(P^*\): foreign price level (asterisk = foreign variable)
- \(pi\): actual inflation rate, \(pi^e\): expected inflation rate
- \(beta\): Phillips-curve sensitivity parameter
- \(alpha_K\): capital share in growth accounting
- Time indexing: \(X(t)\), \(X(t-1)\)

---

# Part A: Formula Sheet with Derivation Logic

## 1) National Income Accounting

### 1.1 GDP (Expenditure Method)
**Formula**
\[
GDP = C + I + G + (X - M)
\]

**Derivation logic**  
All final output produced domestically is bought by households, firms, government, or the foreign sector (net exports). Summing these spending components gives GDP.

**Crucial example**  
If \(C=500\), \(I=150\), \(G=200\), \(X=120\), \(M=100\):  
\[
GDP = 500 + 150 + 200 + (120-100)=870
\]

---

### 1.2 GDP (Income Method)
**Formula**
\[
GDP = Wages + Rent + Interest + Profit + (Indirect\ Taxes - Subsidies) + Depreciation
\]

**Derivation logic**  
Value created in production appears as factor incomes plus production-related adjustments.

**Crucial example**  
Wages \(=400\), Rent \(=50\), Interest \(=40\), Profit \(=120\), NIT \(=30\), Depreciation \(=20\):  
\[
GDP=400+50+40+120+30+20=660
\]

---

### 1.3 Value Added Method
**Formula**
\[
GDP = \sum Value\ Added,\quad Value\ Added = Output - Intermediate\ Inputs
\]

**Derivation logic**  
Gross output double-counts intermediate goods; value-added removes this.

**Crucial example**  
Farmer sells wheat 100, miller sells flour 180, baker sells bread 300.  
Value added = \(100 + (180-100) + (300-180)=300\). GDP contribution = 300.

---

### 1.4 GNP, NDP, NNP Relations
**Formulas**
\[
GNP = GDP + NFIA
\]
\[
NDP = GDP - Depreciation,\quad NNP = GNP - Depreciation
\]
\[
NNP_{FC}=NNP_{MP}-NIT
\]

**Derivation logic**  
Move from domestic to national by adding net foreign factor income; move from gross to net by removing depreciation; move from market prices to factor cost by removing net indirect taxes.

**Crucial example**  
GDP \(=1000\), NFIA \(=20\), Depreciation \(=80\), NIT \(=40\):  
GNP \(=1020\), NNP \(=940\), \(NNP_{FC}=900\).

---

### 1.5 Personal Income and Disposable Income
**Formulas**
\[
PI = NI - Undistributed\ Profits - Corporate\ Tax - Social\ Security + Transfers
\]
\[
DI = PI - Personal\ Direct\ Taxes
\]

**Crucial example**  
NI 900, undistributed profits 60, corp tax 40, social security 20, transfers 30:  
PI \(=810\). If personal taxes \(=110\), then DI \(=700\).

---

## 2) Prices, Inflation, and Real vs Nominal

### 2.1 GDP Deflator
\[
GDP\ Deflator = \frac{Nominal\ GDP}{Real\ GDP}\times 100
\]

**Crucial example**  
Nominal GDP = 1200, Real GDP = 1000 \(\Rightarrow\) Deflator = 120.

### 2.2 CPI
\[
CPI = \frac{Cost\ of\ current\ basket\ at\ current\ prices}{Cost\ of\ same\ basket\ at\ base\ prices}\times100
\]

### 2.3 Inflation Rate
\[
Inflation(t)=\frac{PriceIndex(t)-PriceIndex(t-1)}{PriceIndex(t-1)}\times100
\]

**Crucial example**  
CPI moves from 150 to 162: inflation \(=\frac{12}{150}\times100=8\%\).

---

## 3) Keynesian Goods Market (Income-Expenditure)

### 3.1 Consumption and Saving
\[
C=C_0+cY_d,\quad 0<c<1
\]
\[
S=Y_d-C=-C_0+(1-c)Y_d
\]

### 3.2 Two-Sector Equilibrium
\[
Y=C+I=C_0+cY+I
\]
\[
Y(1-c)=C_0+I\Rightarrow Y^*=\frac{1}{1-c}(C_0+I)
\]

### 3.3 Multiplier
\[
k=\frac{1}{1-c}
\]
If \(Delta A\) is change in autonomous spending:
\[
Delta Y = Delta A + cDelta A + c^2Delta A + ... = \frac{Delta A}{1-c}
\]

**Crucial example**  
If \(c=0.8\), then \(k=5\).  
If autonomous spending rises by 20, equilibrium income rises by \(5\times20=100\).

### 3.4 Government + Tax Version
\[
C=C_0+c(Y-T),\quad Y=C+I+G
\]
\[
Y^*=\frac{1}{1-c}(C_0-cT+I+G)
\]
\[
k_G=\frac{1}{1-c},\quad k_T=-\frac{c}{1-c},\quad k_{BB}=1
\]

**Crucial example**  
If \(c=0.75\), then \(k_G=4\), \(k_T=-3\).  
Increase \(G\) by 10 and \(T\) by 10: net \(Delta Y=40-30=10\).

---

## 4) Money, Banking, and Monetary Core

### 4.1 Quantity Identity
\[
MV=PY
\]

### 4.2 Money Demand (Liquidity Preference Form)
\[
M_d=L(Y,i)=kY-hi
\]

### 4.3 Money Multiplier
Simple:
\[
m=\frac{1}{rr},\quad M=mH
\]

Extended:
\[
m=\frac{1+cdr}{cdr+rr+e}
\]

**Derivation**
\[
M=C+D=(1+cdr)D,\quad H=C+R=(cdr+rr+e)D
\]
\[
m=\frac{M}{H}=\frac{1+cdr}{cdr+rr+e}
\]

**Crucial example**  
If \(cdr=0.2\), \(rr=0.1\), \(e=0.05\):  
\[
m=\frac{1.2}{0.35}=3.43
\]
If \(H=200\), money supply \(\approx 686\).

---

## 5) IS-LM Framework

### 5.1 IS Curve
\[
Y=C(Y-T)+I(i)+G
\]
Higher \(i\) lowers investment, so equilibrium \(Y\) falls.

### 5.2 LM Curve
\[
\frac{M}{P}=L(Y,i)
\]
Higher \(Y\) increases money demand; for fixed \(M/P\), \(i\) must rise.

### 5.3 Policy Direction
- Fiscal expansion (\(G\uparrow\), \(T\downarrow\)) shifts IS right  
- Monetary expansion (\(M\uparrow\)) shifts LM right/down

**Crucial example**  
In a recession with low output, increasing \(G\) shifts IS right; if the central bank also raises \(M\), LM shifts right, giving larger output rise with less interest-rate pressure.

---

## 6) Open Economy Basics

\[
NX = X-M,\quad Y=C+I+G+NX
\]
\[
S-I=NX \quad (\text{simplified setup})
\]
\[
q=e\left(\frac{P^*}{P}\right)
\]

**Crucial example**  
If \(S-I=-50\), then \(NX=-50\): trade deficit with net foreign borrowing.

---

## 7) Labor Market and Phillips Curve

\[
u=\frac{Unemployed}{Labor\ Force}\times100,\quad LFPR=\frac{Labor\ Force}{Working\ Age\ Population}\times100
\]
\[
pi = pi^e - beta(u-u_n)+v
\]

**Interpretation**  
When unemployment is below natural rate, inflation tends to exceed expected inflation.

**Crucial example**  
If \(pi^e=4\%\), \(beta=0.5\), \(u_n=6\%\), \(u=4\%\), \(v=0\):  
\[
pi=4-0.5(4-6)=5\%
\]

---

## 8) Growth Basics

\[
y=\frac{Y}{N}
\]
\[
g_Y \approx g_A + alpha_K g_K + (1-alpha_K)g_L
\]
\[
Doubling\ time \approx \frac{70}{growth\ rate(\%)}
\]

**Crucial example**  
If growth is 7% annually, doubling time \(\approx 10\) years.

---

# Part B: Expanded Theory Notes (with Explanations)

## 1) What Macroeconomics Explains
Macroeconomics studies aggregate outcomes: total income, employment, inflation, interest rates, growth, and external balance. It focuses on interactions among households, firms, government, central bank, and the foreign sector.

### Why this matters
Policy errors arise when short-run demand issues are treated as long-run supply issues (or vice versa). Always identify horizon first.

---

## 2) Circular Flow and National Accounts: Deeper Explanation
- Production generates income; income enables spending; spending sustains production.
- Leakages (saving, taxes, imports) reduce spending flow.
- Injections (investment, government spending, exports) add demand.

If injections exceed leakages, output tends to rise; if leakages dominate, output tends to contract.

---

## 3) Real vs Nominal: Conceptual Depth
- Nominal variables mix quantity and price changes.
- Real variables isolate quantity.
- Inflation is not just “higher prices of one good”; it is a sustained rise in general price level.

### Common confusion
High nominal GDP growth can coexist with weak real growth if inflation is high.

---

## 4) Aggregate Demand and Aggregate Supply (AD-AS)
- **AD shifts**: monetary policy, fiscal policy, external demand, confidence.
- **SRAS shifts**: input costs, taxes on production, supply disruptions.
- **LRAS shifts**: productivity, labor force, institutions, technology.

### Policy implication
Demand-side inflation is often cooled by tighter demand policy; supply-shock inflation requires trade-off management and supply-side support.

---

## 5) Fiscal Policy: More Theory
- Expansionary fiscal policy supports demand during recession.
- Contractionary fiscal policy helps disinflation during overheating.
- Effectiveness depends on MPC, openness, financial conditions, and monetary response.

### Important limits
- Crowding out via higher interest rates
- Debt sustainability constraints
- Implementation lags and political economy constraints

---

## 6) Monetary Policy: More Theory
- Central bank primarily influences short-term rates and expectations.
- Channels: interest-rate channel, bank-lending channel, exchange-rate channel, wealth channel, expectations channel.

### Practical point
Monetary tightening affects inflation with lags; output often slows first, inflation adjusts later.

---

## 7) IS-LM Intuition in Plain Language
- IS tells how spending plans determine output at each interest rate.
- LM tells how money market equilibrium determines interest rates at each output.
- Joint equilibrium explains simultaneous movement of output and rates.

### Typical policy mix logic
During recession: moderate fiscal expansion + monetary support can reduce both unemployment and deep output loss.

---

## 8) Inflation, Expectations, and Unemployment
- Short-run inflation-unemployment tradeoff depends on expectations.
- If expectations adjust upward, temporary tradeoff weakens.
- Supply shocks worsen tradeoff (higher inflation + lower output).

### Crucial insight
Credible policy can lower expected inflation, reducing disinflation cost.

---

## 9) Open Economy Transmission
- Exchange rate affects exports/imports and hence aggregate demand.
- External interest-rate changes can trigger capital flow shifts.
- Terms-of-trade shocks alter domestic inflation and income.

### Fixed vs floating regimes
- Fixed regime: exchange-rate stability, lower monetary autonomy.
- Floating regime: more monetary autonomy, more exchange-rate movement.

---

## 10) Long-Run Growth Theory Essentials
- Persistent growth is productivity-led.
- Capital deepening helps, but diminishing returns require technology/institutions/human capital for sustained growth.
- Quality of governance and rule of law strongly influence long-run income.

---

# Part C: Topic-wise Worked Examples (Crucial)

## Example 1: Recession Stabilization (Keynesian + Multiplier)
A closed economy has \(c=0.8\), output gap = -120.  
Multiplier \(k=5\). Required autonomous demand increase \(\approx 24\) to close gap (\(5\times24=120\)).  
If government chooses fiscal stimulus via \(G\), \(Delta G=24\) can close gap in the simple model.

## Example 2: Inflation Decomposition (Demand vs Supply)
Inflation rose from 4% to 8% while output fell. This pattern is inconsistent with pure demand pull; likely a supply shock (e.g., oil).  
Policy: avoid only aggressive demand compression; combine targeted support + inflation anchoring.

## Example 3: Open-Economy Shock
Global rates rise sharply. Capital outflows cause currency depreciation. Import prices rise \(\rightarrow\) inflation pressure.  
Policy mix: limited rate hike + FX liquidity tools + targeted fiscal relief for imported-energy burden.

---

# Part D: Combined Theory Examples (Integrated)

## Integrated Example A: Fiscal Expansion under Tight Money
Government increases \(G\) to reduce unemployment, but central bank tightens to control inflation expectations.  
Result: output rises less than textbook fiscal multiplier due to higher interest rates (crowding out).  
Learning: policy coordination determines final macro outcome.

## Integrated Example B: Supply Shock + Expectations
Energy shock raises SRAS costs; inflation rises while growth slows. Workers revise inflation expectations upward, shifting short-run Phillips relation unfavorably.  
Learning: credibility, targeted relief, and medium-term productivity response are all needed.

## Integrated Example C: External Deficit + Domestic Demand Boom
Strong domestic demand raises imports, widening \(NX<0\). If financed by volatile capital inflows, economy becomes externally fragile.  
Learning: domestic stabilization must account for balance-of-payments sustainability.

---

# Part E: Short Case Studies

## Case Study 1: 2008-09 Global Financial Crisis (Demand Collapse)
- Shock: credit freeze and confidence collapse
- Macroeconomic effect: output and employment fall sharply
- Policy response: aggressive monetary easing + fiscal stimulus
- Lesson: in deep demand shocks, coordinated stabilization prevents prolonged depression dynamics

## Case Study 2: 1970s Oil Shock (Stagflation Episode)
- Shock: sharp energy price increase (negative supply shock)
- Effect: high inflation + weak growth simultaneously
- Lesson: demand management alone is insufficient; supply-side and expectations management become central

## Case Study 3: Pandemic-era Shock (Supply + Demand + Policy Mix)
- Initial phase: supply disruptions + demand collapse in contact sectors
- Later phase: reopening demand surge + supply bottlenecks \(\rightarrow\) inflation spikes
- Lesson: policy must adapt by phase; same tool is not optimal through all stages of a crisis

## Case Study 4: Emerging-Market Currency Pressure Episode
- Trigger: global tightening and risk-off sentiment
- Effect: depreciation, imported inflation, and external financing stress
- Policy toolkit: rate action, reserve use, macroprudential steps, selective fiscal protection
- Lesson: open-economy macro requires external vulnerability management, not only domestic demand targeting

---

# Part F: High-Yield Revision Pointers
- Start with identities before analysis (\(Y=C+I+G+NX\), \(S-I=NX\), etc.).
- Always separate **level change**, **growth rate**, and **percentage-point change**.
- In every policy answer, mention **transmission channels + lags + trade-offs**.
- State assumptions explicitly (closed/open, fixed prices, expectations static/adaptive, etc.).
- In integrated questions, combine goods market + money market + external sector logic.

---

## End Note
Use this document in three passes:
1. Formula recall  
2. Theory explanation in your own words  
3. Case-based application (policy reasoning and trade-offs)

