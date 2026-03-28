# Backend Development Club Curriculum

**Duration:** 1.5 Months (6 Weeks)\
**Goal:** Take students from basic backend understanding to building and
deploying a production-style backend service.

This program assumes students **already know JavaScript fundamentals**.

The curriculum focuses on:

-   Backend architecture
-   Databases
-   Authentication & security
-   Caching & asynchronous processing
-   Production deployment
-   High‑level distributed systems concepts

All learning feeds into **one final monolith backend project** developed
throughout the program.

------------------------------------------------------------------------

# Tech Stack Used

**Backend** - Node.js - Express (core teaching framework) - NestJS
(conceptual / optional extension)

**Database** - PostgreSQL - Prisma ORM

**Infrastructure** - Redis - Docker

**Utilities** - Zod / Joi - bcrypt - jsonwebtoken

------------------------------------------------------------------------

# Program Structure

The course is structured so that **each week introduces concepts that
immediately expand the final project**.

Students incrementally build a **production-style monolithic backend
system**.

Timeline Overview:

  Week   Focus
  ------ -------------------------------------
  1      Web & Backend Foundations
  2      Express Architecture
  3      Databases
  4      Authentication & Security
  5      Caching & Async Systems
  6      Deployment & System Design Concepts

------------------------------------------------------------------------

# Final Monolith Project

Students will build a **production-style SaaS backend API**.

Example system:

**DevCollab API --- Developer Collaboration Platform**

Core features:

Authentication

    signup
    login
    JWT tokens

Users

    profiles
    roles
    permissions

Projects

    create project
    invite collaborators
    project settings

Tasks

    task CRUD
    assignment
    status tracking

Performance

    Redis caching
    background workers

Deployment

    Docker
    cloud deployment

------------------------------------------------------------------------

# Week 1 --- Backend & Web Foundations

## Goal

Understand how backend systems communicate with the internet and
clients.

## Topics

### How the Internet Works

-   HTTP / HTTPS
-   TCP basics
-   DNS resolution
-   Request / response lifecycle
-   Headers
-   Status codes
-   Cookies vs Sessions
-   REST architecture

Example API structure:

    GET /users
    GET /users/:id
    POST /users
    PATCH /users/:id
    DELETE /users/:id

### Node.js Runtime

Topics:

-   Node architecture
-   Event loop
-   Non‑blocking I/O
-   Modules
-   Environment variables
-   npm ecosystem

### API Development Tools

Students learn to test APIs using:

-   Postman
-   curl
-   browser dev tools

------------------------------------------------------------------------

## Project Milestone

Create the **basic API server**.

Features:

-   Express server
-   Basic routing
-   Project structure

Example structure:

    src
     ├ controllers
     ├ routes
     ├ services
     ├ config
     ├ middleware
     └ server.js

------------------------------------------------------------------------

# Week 2 --- Express Backend Architecture

## Goal

Teach how professional backend services are structured.

------------------------------------------------------------------------

## Express Core Concepts

### Routing

    GET
    POST
    PUT
    PATCH
    DELETE

### Middleware

-   request logging
-   authentication
-   validation
-   error handling

Middleware flow:

    Request
     ↓
    Middleware
     ↓
    Controller
     ↓
    Response

------------------------------------------------------------------------

## Backend Architecture

Introduce layered architecture.

    Controller
       ↓
    Service
       ↓
    Repository
       ↓
    Database

Concepts:

-   separation of concerns
-   request lifecycle
-   validation
-   error propagation

------------------------------------------------------------------------

## Request Validation

Tools:

-   Zod
-   Joi

Students learn:

-   schema validation
-   sanitizing input
-   preventing malformed requests

------------------------------------------------------------------------

## Project Milestone

Implement main API resources:

    Users
    Posts
    Comments

Students now have a **fully functional REST API** (in-memory storage for
now).

------------------------------------------------------------------------

# Week 3 --- Databases

## Goal

Teach persistent storage and relational modeling.

------------------------------------------------------------------------

# SQL Fundamentals

Recommended database:

**PostgreSQL**

Topics:

### Basic Queries

    SELECT
    INSERT
    UPDATE
    DELETE

### Relationships

-   one‑to‑one
-   one‑to‑many
-   many‑to‑many

### Constraints

-   primary keys
-   foreign keys
-   unique constraints

------------------------------------------------------------------------

# Intermediate SQL

Students learn:

-   joins
-   indexes
-   transactions
-   normalization

Important concepts:

-   ACID properties
-   query performance
-   indexing strategies

------------------------------------------------------------------------

# ORM Layer

Recommended ORM:

**Prisma**

Topics:

-   models
-   migrations
-   relations
-   Prisma client queries

Architecture:

    Database
       ↓
    ORM
       ↓
    Backend API

------------------------------------------------------------------------

## Project Milestone

Integrate PostgreSQL.

Features added:

-   persistent data
-   relational models
-   migrations
-   database seeding

------------------------------------------------------------------------

# Week 4 --- Authentication & Security

## Goal

Teach how real production systems manage users securely.

------------------------------------------------------------------------

## Authentication

Students implement:

-   password hashing using bcrypt
-   login system
-   JWT authentication
-   access tokens
-   refresh tokens

Authentication flow:

    Client
     ↓
    Login Request
     ↓
    JWT Token Issued
     ↓
    Authenticated Requests

------------------------------------------------------------------------

## Authorization

Topics:

-   role-based access control
-   permission checks

Example roles:

    admin
    member
    viewer

------------------------------------------------------------------------

## Security Practices

Topics:

-   CORS configuration
-   rate limiting
-   Helmet security headers
-   preventing SQL injection
-   input validation

------------------------------------------------------------------------

## Project Milestone

Add authentication features:

    signup
    login
    protected routes
    roles
    permissions

------------------------------------------------------------------------

# Week 5 --- Performance & Asynchronous Systems

## Goal

Teach performance optimization and background processing.

------------------------------------------------------------------------

# Caching

Introduce **Redis**.

Topics:

-   cache-aside pattern
-   API response caching
-   session caching

Architecture:

    Client
     ↓
    Backend
     ↓
    Redis Cache
     ↓
    Database

Benefits:

-   faster responses
-   reduced database load

------------------------------------------------------------------------

# Background Jobs

Concepts:

-   asynchronous processing
-   workers
-   job queues

Tools:

-   BullMQ
-   Redis queues

Example workflow:

    User uploads image
     ↓
    Queue
     ↓
    Worker processes image

------------------------------------------------------------------------

## Project Milestone

Add:

-   Redis caching
-   job queues
-   background workers

------------------------------------------------------------------------

# Week 6 --- Production Backend Engineering

## Goal

Teach how backend systems run in production.

------------------------------------------------------------------------

# Docker

Students learn containerization.

Topics:

-   Docker images
-   containers
-   Dockerfile
-   docker-compose

Example architecture:

    Backend Container
    PostgreSQL Container
    Redis Container

------------------------------------------------------------------------

# Deployment Concepts

Students learn:

-   environment configuration
-   production builds
-   secrets management

Deployment targets:

-   AWS
-   Railway
-   Render
-   DigitalOcean

------------------------------------------------------------------------

# Observability

Production systems require monitoring.

Topics:

Logging

-   Winston
-   Pino

Metrics

-   Prometheus
-   Grafana

Error tracking

-   Sentry

------------------------------------------------------------------------

# Scaling Concepts (Conceptual)

Students are introduced to:

## Load Balancing

    User
     ↓
    Load Balancer
     ↓
    Multiple Backend Servers

Strategies:

-   round robin
-   least connections

------------------------------------------------------------------------

## Database Replication

    Primary Database
     ↓
    Read Replicas

Used for read-heavy systems.

------------------------------------------------------------------------

## Sharding

Splitting data across multiple databases.

Example:

    Users 1–1M → shard 1
    Users 1M–2M → shard 2

Challenges:

-   cross-shard queries
-   consistency

------------------------------------------------------------------------

## Microservices (Overview)

Architecture:

    Auth Service
    User Service
    Payments Service
    Notification Service

Communication:

-   REST
-   message queues
-   gRPC

Students are introduced conceptually but **do not implement
microservices**.

------------------------------------------------------------------------

# System Design Introduction

Students are introduced to large-scale architecture.

Example design problems:

-   Design Twitter
-   Design Uber
-   Design WhatsApp
-   Design Netflix

Concepts:

-   CAP theorem
-   eventual consistency
-   fault tolerance
-   idempotency
-   circuit breakers

------------------------------------------------------------------------

# Capstone Completion

Students finish the **DevCollab API backend**.

Final system includes:

-   Express backend
-   PostgreSQL database
-   Prisma ORM
-   JWT authentication
-   Redis caching
-   background job workers
-   Docker deployment
-   production configuration

------------------------------------------------------------------------

# Expected Outcomes

By the end of the program students will understand:

-   backend architecture
-   REST API design
-   SQL database modeling
-   authentication systems
-   caching strategies
-   asynchronous workers
-   containerized deployment
-   large scale backend concepts

Students should be able to **build real backend projects and apply for
backend internships**.

------------------------------------------------------------------------

# Optional Advanced Topics (For Interested Students)

These are not required but may be discussed briefly.

-   NestJS architecture
-   GraphQL APIs
-   gRPC
-   distributed consensus (Raft / Paxos)
-   CQRS
-   event sourcing
