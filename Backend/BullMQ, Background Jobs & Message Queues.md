## 1. Introduction

**BullMQ** is a fast, robust, Redis-backed job queue system for Node.js (also available for Python, Elixir, and PHP). It is the modern rewrite of the older "Bull" library, built for high throughput and horizontal scalability.

**Why use BullMQ?**
- **Offload heavy work**: Keep your API fast by moving slow tasks (email, image processing, PDF generation) to background workers.
- **Reliability**: Jobs survive server crashes. If a worker dies mid-job, BullMQ will retry it automatically.
- **Rate limiting**: Prevent downstream services (SMTP, AI APIs) from being overwhelmed.
- **Scheduling**: Run jobs once, with a delay, or on a recurring cron schedule.
- **Observability**: Track job states, progress, and failures in real-time.

**Prerequisites**: Redis server running (local or cloud). BullMQ uses Redis as both the message broker and persistence layer.

---

## 2. Core Concepts

### Queue
A **Queue** is the producer side. It holds jobs waiting to be processed. You add jobs to a queue from your main application (e.g., an Express API).

```js
import { Queue } from 'bullmq';

const emailQueue = new Queue('emails', {
  connection: { host: 'localhost', port: 6379 }
});

// Add a job
await emailQueue.add('send-welcome', { to: 'user@example.com' });
```

### Job
A **Job** is a unit of work. It has:
- `name`: A string identifier (e.g., `'send-welcome'`, `'process-image'`).
- `data`: The payload (any JSON-serializable object).
- `opts`: Configuration like `delay`, `priority`, `attempts`, `backoff`.

```js
await emailQueue.add('send-welcome', 
  { to: 'user@example.com', userId: 123 }, 
  { 
    delay: 5000,           // wait 5 seconds before processing
    priority: 1,          // lower number = higher priority
    attempts: 3,          // retry up to 3 times on failure
    backoff: { type: 'exponential', delay: 1000 }
  }
);
```

### Worker
A **Worker** is the consumer. It connects to Redis, polls the queue, and runs your processor function. Workers run in separate processes (or even separate servers) from your API.

```js
import { Worker } from 'bullmq';

const worker = new Worker('emails', async (job) => {
  // job.id, job.name, job.data are available
  await sendEmail(job.data.to, 'Welcome!');
  return { sentAt: new Date().toISOString() }; // return value stored in job
}, {
  connection: { host: 'localhost', port: 6379 },
  concurrency: 5, // process up to 5 jobs in parallel
  limiter: {
    max: 100,        // max 100 jobs
    duration: 60000  // per 60 seconds
  }
});
```

### Events
BullMQ emits events that let you react to job lifecycle changes. There are two ways to listen:

**1. Worker-level events** (directly on the worker instance):
```js
worker.on('completed', (job) => {
  console.log(`Job ${job.id} completed`);
});

worker.on('failed', (job, err) => {
  console.error(`Job ${job.id} failed:`, err.message);
});
```

**2. QueueEvents** (global pub/sub for any process to listen):
```js
import { QueueEvents } from 'bullmq';

const queueEvents = new QueueEvents('emails', { connection });

queueEvents.on('completed', ({ jobId, returnvalue }) => {
  console.log(`Global listener: Job ${jobId} done`);
});

queueEvents.on('failed', ({ jobId, failedReason }) => {
  console.error(`Global listener: Job ${jobId} failed: ${failedReason}`);
});
```

### Job States
A job moves through states in Redis:
- `waiting` → `active` → `completed` / `failed`
- Can also be `delayed` (if scheduled for later) or `paused`.

---

## 3. Quick Start

### Step 1: Install & Run Redis

```bash
# Using Docker
docker run -d -p 6379:6379 --name redis redis:7-alpine

# Or install locally
npm install -g redis-server
redis-server
```

### Step 2: Install BullMQ

```bash
npm init -y
npm install bullmq ioredis
```

### Step 3: Create the Queue & Add Jobs (`producer.js`)

```js
import { Queue } from 'bullmq';

const connection = { host: '127.0.0.1', port: 6379 };

const myQueue = new Queue('tasks', { connection });

async function addJobs() {
  await myQueue.add('send-email', { to: 'alice@example.com', subject: 'Hello' });
  await myQueue.add('process-image', { imageUrl: 'https://example.com/photo.jpg' });
  console.log('Jobs added!');
  await myQueue.close();
}

addJobs();
```

### Step 4: Create the Worker (`worker.js`)

```js
import { Worker } from 'bullmq';

const connection = { host: '127.0.0.1', port: 6379 };

const worker = new Worker('tasks', async (job) => {
  console.log(`Processing ${job.name} (${job.id})...`);
  
  if (job.name === 'send-email') {
    console.log(`Sending email to ${job.data.to}`);
    // simulate async work
    await new Promise(r => setTimeout(r, 1000));
    return { status: 'email-sent' };
  }
  
  if (job.name === 'process-image') {
    console.log(`Processing image: ${job.data.imageUrl}`);
    await new Promise(r => setTimeout(r, 2000));
    return { status: 'image-resized' };
  }
}, { connection });

worker.on('completed', (job, result) => {
  console.log(`✅ Job ${job.id} completed with result:`, result);
});

worker.on('failed', (job, err) => {
  console.error(`❌ Job ${job.id} failed:`, err.message);
});

console.log('Worker started...');
```

### Step 5: Run

```bash
# Terminal 1
node worker.js

# Terminal 2
node producer.js
```

---

## 4. Complete Practice Questions

### Module A: BullMQ Setup & Implementation (Code: 2 hours)

#### Exercise 1: Project Setup & Redis Connection
**Goal**: Initialize a Node.js project with TypeScript, BullMQ, and Redis.

**Requirements**:
1. Create a new folder `bullmq-practice`.
2. Initialize with `npm init -y` and install `bullmq`, `ioredis`, `typescript`, `@types/node`, `ts-node`.
3. Create a `tsconfig.json` with `"module": "NodeNext"` and `"target": "ES2022"`.
4. Create `config/redis.ts` that exports a Redis connection object.
5. Write a health-check script `scripts/check-redis.ts` that connects to Redis and prints `Redis connected: PONG`.

**Acceptance Criteria**:
- Running `npx ts-node scripts/check-redis.ts` prints a successful Redis ping.

---

#### Exercise 2: Create a Queue for Async Tasks
**Goal**: Build a reusable queue factory.

**Requirements**:
1. Create `queues/email.queue.ts` that exports a `Queue` instance named `'email-queue'`.
2. Create `queues/image.queue.ts` that exports a `Queue` instance named `'image-queue'`.
3. Both should use the shared Redis connection config.
4. Create a utility `queues/index.ts` that exports both queues and a `closeAllQueues()` function.

**Acceptance Criteria**:
- You can import and add jobs to both queues without errors.

---

#### Exercise 3: Add a Job to Queue (Send Email)
**Goal**: Implement job creation with options.

**Requirements**:
1. Create `services/email.service.ts` with a function `scheduleEmail(data, options?)`.
2. The function should add a job to `'email-queue'` with name `'send-email'`.
3. Support these `options`:
   - `delay`: number (ms) — delay before processing
   - `priority`: number — lower is higher priority
   - `attempts`: number — retry count
   - `backoff`: `{ type: 'fixed' | 'exponential', delay: number }`
4. Return the `job.id` to the caller.

**Acceptance Criteria**:
- `scheduleEmail({ to: 'test@test.com', subject: 'Hi' }, { delay: 5000, attempts: 3 })` returns a valid job ID.
- The job appears in Redis with the correct delay.

---

#### Exercise 4: Create a Worker to Process Jobs
**Goal**: Build a worker that handles multiple job types.

**Requirements**:
1. Create `workers/email.worker.ts` that creates a `Worker` for `'email-queue'`.
2. The processor should handle two job names:
   - `'send-email'`: Log `Sending email to <to>`, simulate 1s delay, return `{ sent: true, timestamp: ISOString }`.
   - `'send-bulk-email'`: Log `Sending bulk email to <count> recipients`, simulate 2s delay, return `{ sentCount: number }`.
3. Use `concurrency: 3`.
4. Add worker-level event listeners for `completed` and `failed`.

**Acceptance Criteria**:
- Running the worker processes jobs and logs completion/failure.
- The worker handles both job types correctly based on `job.name`.

---

#### Exercise 5: Handle Job Completion and Failures
**Goal**: Implement robust error handling and retry logic.

**Requirements**:
1. Update the email worker to throw an error if `job.data.to` is missing or invalid.
2. Configure the queue so failed jobs retry with **exponential backoff** (1s, 2s, 4s).
3. After max retries, log the final failure and store the `failedReason`.
4. Create a `scripts/retry-test.ts` that:
   - Adds a job with invalid data (no `to` field)
   - Adds a job with valid data
   - Keeps the worker running until both jobs settle
5. Create a cleanup script `scripts/clean-failed.ts` that removes all failed jobs older than 24 hours.

**Acceptance Criteria**:
- Invalid job fails 3 times with exponential backoff, then moves to `failed`.
- Valid job completes on first attempt.
- Cleanup script removes old failed jobs.

---

#### Exercise 6: Job Progress & Sandboxed Workers (Bonus)
**Goal**: Report progress for long-running jobs.

**Requirements**:
1. Create a worker for `'image-queue'` that processes `'resize-image'` jobs.
2. The image processing has 4 steps: download, resize, compress, upload.
3. After each step, call `job.updateProgress(stepIndex / 4 * 100)`.
4. Create a `QueueEvents` listener in `events/image.events.ts` that logs progress updates.
5. (Advanced) Run the worker in a **sandboxed** process using `sandbox: { processor: './processors/image.processor.ts' }` to isolate crashes.

**Acceptance Criteria**:
- Progress events are logged in real-time.
- If the sandboxed processor crashes, the main process stays alive and BullMQ retries the job.

---

### Module B: Job Queues in Express API (Code: 1 hour)

#### Exercise 7: Trigger Async Job from API Endpoint
**Goal**: Integrate BullMQ into an Express application.

**Requirements**:
1. Create an Express app in `app.ts`.
2. Add `POST /api/emails` endpoint with body `{ to, subject, body, delay? }`.
3. The endpoint should:
   - Validate the request body (all fields required except `delay`).
   - Add a job to `'email-queue'` using `scheduleEmail()`.
   - Return `{ success: true, jobId: string, message: 'Email queued' }`.
4. Add `POST /api/images/resize` endpoint with body `{ imageUrl, width, height }`.
   - Add a job to `'image-queue'`.
   - Return `{ success: true, jobId: string }`.
5. Add global error handling middleware.

**Acceptance Criteria**:
- `POST /api/emails` with valid JSON returns a `jobId`.
- `POST /api/emails` with missing `to` returns `400 Bad Request`.
- Jobs are visible in Redis immediately after the API responds.

---

#### Exercise 8: Track Job Status
**Goal**: Allow clients to poll for job status.

**Requirements**:
1. Add `GET /api/jobs/:jobId/status` endpoint.
2. The endpoint should:
   - Accept a `queueName` query param (`email-queue` or `image-queue`).
   - Fetch the job using `queue.getJob(jobId)`.
   - If job not found, return `404`.
   - Return the job state: `waiting | active | completed | failed | delayed`.
   - If `completed`, include `returnvalue`.
   - If `failed`, include `failedReason`.
3. Add `GET /api/queues/:queueName/stats` that returns:
   - `waiting`, `active`, `completed`, `failed`, `delayed` counts.

**Acceptance Criteria**:
- After hitting `POST /api/emails`, you can poll `GET /api/jobs/<jobId>/status?queueName=email-queue` and see it move from `waiting` → `active` → `completed`.

---

#### Exercise 9: Practice Project — End-to-End Implementation
**Goal**: Combine everything into a working system.

**Requirements**:
Build an **Image Processing Service** with the following architecture:

```
src/
├── config/
│   └── redis.ts
├── queues/
│   ├── email.queue.ts
│   └── image.queue.ts
├── workers/
│   ├── email.worker.ts
│   └── image.worker.ts
├── services/
│   ├── email.service.ts
│   └── image.service.ts
├── routes/
│   ├── email.routes.ts
│   └── image.routes.ts
├── controllers/
│   ├── email.controller.ts
│   └── image.controller.ts
├── app.ts
└── server.ts
```

**Functional Requirements**:
1. **Email Service**:
   - `POST /api/emails/send` → queues a welcome email.
   - `POST /api/emails/bulk` → accepts an array of recipients and queues a bulk job.
   - Worker sends emails (simulated with `setTimeout` and console.log).
   - Failed emails retry 3 times, then move to a dead-letter queue (create `'email-dead-letter'` queue and move failed jobs there).

2. **Image Processing Service**:
   - `POST /api/images/resize` → queues a resize job.
   - `POST /api/images/convert` → queues a format-conversion job (e.g., PNG to WebP).
   - Worker processes images with progress updates (0%, 25%, 50%, 75%, 100%).
   - Rate limit image processing to **10 jobs per minute** to simulate a paid API.

3. **Monitoring**:
   - `GET /api/health` → checks Redis + queue connectivity.
   - `GET /api/dashboard` → returns JSON with all queue stats and recent 10 completed/failed jobs.

**Bonus Challenges**:
- Use `FlowProducer` to create a job flow: `resize-image` → `optimize-image` → `upload-image` (each step is a separate queue, dependent on the previous).
- Implement a cron job using `queue.add('cleanup', {}, { repeat: { pattern: '0 0 * * *' } })` that runs daily to clean old completed jobs.

---

## Quick Reference: Common BullMQ Patterns

| Pattern | Code Snippet |
|---|---|
| **Delayed Job** | `queue.add('name', data, { delay: 60000 })` |
| **Recurring Job** | `queue.add('name', data, { repeat: { pattern: '0 9 * * *' } })` |
| **Priority** | `queue.add('name', data, { priority: 1 })` |
| **Rate Limit** | Worker option: `{ limiter: { max: 10, duration: 1000 } }` |
| **Retry + Backoff** | `queue.add('name', data, { attempts: 3, backoff: { type: 'exponential', delay: 1000 } })` |
| **Job Progress** | Inside worker: `await job.updateProgress(50)` |
| **Get Job Status** | `const state = await job.getState()` |
| **Remove Job** | `await job.remove()` |

---

## Recommended File Structure for Practice

```
bullmq-practice/
├── src/
│   ├── config/
│   │   └── redis.ts
│   ├── queues/
│   │   ├── email.queue.ts
│   │   └── image.queue.ts
│   ├── workers/
│   │   ├── email.worker.ts
│   │   └── image.worker.ts
│   ├── services/
│   │   ├── email.service.ts
│   │   └── image.service.ts
│   ├── routes/
│   │   ├── email.routes.ts
│   │   └── image.routes.ts
│   ├── controllers/
│   │   ├── email.controller.ts
│   │   └── image.controller.ts
│   ├── events/
│   │   └── queue.events.ts
│   ├── app.ts
│   └── server.ts
├── scripts/
│   ├── check-redis.ts
│   ├── retry-test.ts
│   └── clean-failed.ts
├── docker-compose.yml
├── package.json
└── tsconfig.json
```

---

## Docker Compose for Local Development

```yaml
# docker-compose.yml
version: '3.8'
services:
  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"
    volumes:
      - redis-data:/data
  redis-insight:
    image: redislabs/redisinsight:latest
    ports:
      - "8001:8001"
    depends_on:
      - redis
volumes:
  redis-data:
```

Run with `docker-compose up -d` and visit `http://localhost:8001` to visually inspect your queues.

---
