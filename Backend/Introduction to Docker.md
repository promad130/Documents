]# Part 1: Docker Containerization

## 1. Core Concepts

Before writing commands, understand the three building blocks:

| Concept | What it is | Analogy |
|---|---|---|
| **Image** | A read-only template containing your app code, runtime, libraries, and dependencies. | A **Class** in programming. |
| **Container** | A runnable instance of an image. It is isolated but shares the host OS kernel. | An **Object** instantiated from a class. |
| **Layer** | Images are built in layers. Each instruction in a `Dockerfile` creates a new layer on top of the previous one. Layers are cached; if a layer doesn't change, Docker reuses it. | **Git commits** stacked on top of each other. |

**Why layers matter:** If you change only your application source code, Docker rebuilds only the layers after the `COPY` command. The `RUN npm install` layer is fetched from cache, making rebuilds extremely fast.

---

## 2. Dockerfile for a Node.js Express App

Assume you have a basic Express app:

```text
my-express-app/
├── package.json
├── package-lock.json
└── index.js
```

### Step A: Create a `.dockerignore`
This prevents local `node_modules` and secrets from being copied into the image.

```text
# .dockerignore
node_modules
npm-debug.log
Dockerfile
.dockerignore
.git
.env
```

### Step B: Write the Dockerfile
We will use a **multi-stage build**. This is the industry standard for Node.js because it separates the *build/dependency* environment from the *production* environment, keeping the final image small and secure.

```dockerfile
# syntax=docker/dockerfile:1

# ------------------
# STAGE 1: Dependencies
# ------------------
FROM node:18-alpine AS dependencies

WORKDIR /app

# Copy dependency manifests first (leverages layer caching)
COPY package*.json ./

# Install ONLY production dependencies
RUN npm ci --only=production

# ------------------
# STAGE 2: Production
# ------------------
FROM node:18-alpine AS production

# Security: Run as non-root user. The 'node' user exists in official Node images.
USER node

# Set working directory and ensure the 'node' user owns it
WORKDIR /app

# Copy dependencies from the first stage. --chown ensures correct permissions.
COPY --chown=node:node --from=dependencies /app/node_modules ./node_modules

# Copy application code
COPY --chown=node:node . .

# Declare that the container listens on port 3000
EXPOSE 3000

# Set environment
ENV NODE_ENV=production

# Run the app
CMD ["node", "index.js"]
```

**Key syntax explained:**
- `FROM ... AS <name>`: Names a stage so you can copy from it later.
- `RUN npm ci --only=production`: `ci` is faster and stricter than `install`. `--only=production` skips devDependencies.
- `COPY --from=dependencies`: Pulls files from an earlier stage into the current stage.
- `USER node`: Drops root privileges. If an attacker breaks into the container, they are not root on the host.

### Step C: Build and Run

```bash
# Build the image and tag it as 'myapp'
docker build -t myapp .

# Run the container, mapping host port 3000 to container port 3000
docker run -p 3000:3000 myapp
```

Visit `http://localhost:3000` in your browser.

**Background mode:**
```bash
docker run -d -p 3000:3000 --name myapp_container myapp
```

**View logs:**
```bash
docker logs -f myapp_container
```

**Stop and remove:**
```bash
docker stop myapp_container
docker rm myapp_container
```

---

## 3. Docker Best Practices

### Minimizing Image Size
1. **Use Alpine or Distroless base images:** `node:18-alpine` is ~40MB vs `node:18` at ~900MB.
2. **Multi-stage builds:** As shown above, build artifacts and dev tools do not ship to production.
3. **Clean up in the same layer:**
   ```dockerfile
   RUN apk add --no-cache some-package
   ```
   The `--no-cache` flag prevents the package manager from storing an index in the layer.

### Security
1. **Never run as root:** Always use `USER` (or the `node` user for official images).
2. **Scan your images:** Use Docker Scout or Snyk.
   ```bash
   docker scout cves myapp
   ```
3. **Don't bake secrets into images:** Use environment variables at runtime (shown in the Compose section below).
4. **Use specific image tags:** Never use `FROM node:latest`. It is unpredictable and breaks reproducibility.

---

# Part 2: Docker Compose Multi-Service Setup

## 1. Core Concepts

Docker Compose manages multi-container applications using a single YAML file.

| Concept | Explanation |
|---|---|
| **Service** | A definition for a container (e.g., `api`, `db`, `cache`). |
| **Networking** | Compose automatically creates a network. Services communicate using their service names as hostnames (e.g., `db` resolves to the PostgreSQL container). |
| **Volumes** | Persistent storage. When a container is destroyed, data in a volume survives. Essential for databases. |
| **Environment Variables** | Injected into containers at runtime. Can be defined in the YAML or an external `.env` file. |

---

## 2. Full Setup: Node.js + PostgreSQL + Redis

### Project Structure
```text
my-project/
├── docker-compose.yml
├── .env
├── .dockerignore
├── Dockerfile
├── package.json
└── index.js
```

### The Compose File (`docker-compose.yml`)

```yaml
# No 'version:' key needed for modern Compose Specification
services:
  # ------------------
  # Node.js Express API
  # ------------------
  api:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: express_api
    ports:
      - "3000:3000"
    environment:
      - NODE_ENV=production
      - DATABASE_URL=postgres://${DB_USER}:${DB_PASSWORD}@db:5432/${DB_NAME}
      - REDIS_URL=redis://cache:6379
    depends_on:
      - db
      - cache
    networks:
      - app_network
    restart: unless-stopped

  # ------------------
  # PostgreSQL Database
  # ------------------
  db:
    image: postgres:15-alpine
    container_name: postgres_db
    environment:
      POSTGRES_USER: ${DB_USER}
      POSTGRES_PASSWORD: ${DB_PASSWORD}
      POSTGRES_DB: ${DB_NAME}
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - app_network
    restart: unless-stopped

  # ------------------
  # Redis Cache
  # ------------------
  cache:
    image: redis:7-alpine
    container_name: redis_cache
    networks:
      - app_network
    restart: unless-stopped

# ------------------
# Named Volumes
# ------------------
volumes:
  postgres_data:
    driver: local

# ------------------
# Networks
# ------------------
networks:
  app_network:
    driver: bridge
```

### Environment File (`.env`)
Keep secrets out of the YAML.

```env
DB_USER=myuser
DB_PASSWORD=strongpassword123
DB_NAME=myappdb
```

**Important:** Add `.env` to your `.gitignore` and `.dockerignore`.

---

## 3. How It Works

### Networking
Compose creates a bridge network called `app_network`. Inside this network:
- Your Express app connects to Postgres using the hostname `db`.
- It connects to Redis using the hostname `cache`.
- You do not need to expose Postgres or Redis ports to the host unless you want to inspect them with a local GUI tool. Only `api` exposes `3000`.

### Volume Persistence
`postgres_data` is a named volume managed by Docker. Even if you run `docker compose down` and delete the `db` container, the data remains on disk. When you bring the stack back up, Postgres reattaches the volume.

### Dependency Startup
`depends_on` ensures the `api` container starts *after* `db` and `cache` containers are created. **However**, it does **not** wait for the database to be *ready* to accept connections. For a production-grade app, you should add a small retry loop or a "wait-for-it" script in your Node.js startup code.

---

## 4. Commands to Run

Make sure you are in the directory containing `docker-compose.yml`.

```bash
# Build images and start all services in the foreground (logs visible)
docker-compose up

# Build images and start in detached mode (background)
docker-compose up -d

# Rebuild images if you changed the Dockerfile or source code
docker-compose up -d --build

# View logs for all services
docker-compose logs -f

# View logs for just the API
docker-compose logs -f api

# Stop and remove containers (keeps volumes)
docker-compose down

# Stop and remove containers AND volumes (WARNING: deletes database data)
docker-compose down -v

# Execute a command inside the running API container
docker-compose exec api sh
```

---

## Pop!_OS Specific Notes

1. **Installation:** Pop!_OS is Debian-based. You can install Docker via `apt`, but for the latest features, use the official Docker repository:
   ```bash
   sudo apt update
   sudo apt install docker.io docker-compose-plugin
   ```
   Or follow the [official Docker Engine install guide for Ubuntu](https://docs.docker.com/engine/install/ubuntu/) (it works identically on Pop!_OS).

2. **Permissions:** After installing, add your user to the `docker` group so you don't need `sudo` for every command:
   ```bash
   sudo usermod -aG docker $USER
   newgrp docker
   ```

3. **Compose command:** Modern Docker uses `docker compose` (space, no hyphen). If your Pop!_OS install only provides the older standalone binary, use `docker-compose` (hyphen). Both commands accept the same arguments.

---

## Quick Reference Checklist

- [ ] `.dockerignore` exists and ignores `node_modules` and `.env`.
- [ ] `Dockerfile` uses a small base image (`alpine`) and a non-root `USER`.
- [ ] `Dockerfile` uses multi-stage builds to keep production images lean.
- [ ] `docker-compose.yml` uses a named volume for Postgres.
- [ ] Secrets are passed via environment variables, not hardcoded in images.
- [ ] You run `docker-compose up -d --build` after code changes.

This setup gives you a production-oriented foundation. Start with `docker-compose up`, verify your services talk to each other, and then iterate on optimizing image size and security.