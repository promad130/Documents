**Course Context:** CS F212 Group Project  
**Project Title:** PlateMate  

---

## 1. Executive Summary

PlateMate is a full-stack, microservices-oriented platform designed to connect donors and claimers for sharing useful items (with a strong food-sharing focus) in a locality-aware and trust-enabled ecosystem. The project emphasizes reliable backend behavior, modular frontend development, and robust database design.  

The system is implemented through:
- **React Native + TypeScript frontend** for mobile-friendly user interactions.
- **Node.js backend microservices** for scalable domain separation.
- **PostgreSQL as the core data layer**, split into five domain-specific databases.
- **RabbitMQ/Redis event bus integration** for asynchronous communication between services.
- **Docker Compose setup** to orchestrate and run the complete stack in a reproducible way.

A central architectural policy was followed:  
> **No foreign keys across microservice databases**.  
Each service owns its data independently, and cross-service synchronization happens through published/subscribed events.

This report elaborates the complete implementation approach, feature modules, privileges, and detailed contribution of each team member.

---

## 2. Project Vision and Problem Statement

### 2.1 Motivation
Many users have surplus items (especially food, books, electronics, furniture) that remain unused while nearby users may need them. Traditional donation channels are often slow or non-localized. PlateMate addresses this by offering:
- proximity-based discovery,
- quick claim mechanisms,
- integrated chat coordination,
- trust and reputation signals,
- and moderation tools for safety.

### 2.2 Core Goals
1. Build a **location-aware donation/claiming ecosystem**.
2. Ensure **data reliability** under concurrent claim requests.
3. Provide **clear service boundaries** through microservices.
4. Implement **event-driven workflows** for scalable cross-service operations.
5. Deliver an intuitive frontend with list + map exploration and real-time-like interaction patterns.

---

## 3. System Architecture Overview

PlateMate follows a **microservices architecture** with isolated domain databases and asynchronous communication.

### 3.1 High-Level Components
- **Frontend App (React Native)**
- **API Gateway**
- **Service Layer**
  - User & Location Service
  - Inventory Service
  - Claim Service
  - Reputation Service
  - Messaging Service
- **Event Bus (RabbitMQ/Redis-based Pub/Sub flow)**
- **PostgreSQL Databases (5 isolated DBs)**

### 3.2 Architectural Rule
There are zero cross-database foreign keys.  
For example, if Claim Service stores a `Post_ID` or `User_ID`, it is stored as a plain integer reference and validated/maintained through event workflows and service logic.

### 3.3 Why this architecture?
- Enables independent scaling and deployment of services.
- Prevents tight coupling between domains.
- Improves maintainability and service ownership.
- Supports asynchronous resilience patterns for distributed systems.

---

## 4. Technology Stack and Rationale

### 4.1 Frontend
- **Primary Language:** TypeScript (~99.4% in frontend repo)
- **Framework:** React Native
- **Pattern:** MVVM-inspired hooks and modular screen composition

**Rationale:** Type safety, maintainable component state, reusable view-model logic, and better developer ergonomics for medium-scale mobile applications.

### 4.2 Backend
- **Primary Language:** JavaScript (Node.js ecosystem)
- **Architecture:** Microservices with API Gateway
- **Inter-service communication:** Event-driven Pub/Sub

**Rationale:** Fast prototyping, wide ecosystem, strong JSON/event compatibility, and easy integration with queues and PostgreSQL.

### 4.3 Database
- **RDBMS:** PostgreSQL
- **Schema style:** service-local normalization and constraints
- **Advanced SQL:** triggers, procedures, unique partial indexes, check constraints, transactional locking

**Rationale:** strong ACID guarantees, expressive SQL features, and robust production-grade transactional semantics.

### 4.4 Orchestration and Runtime Ops
- **Docker Compose** for local full-stack deployment.
- **Queue broker + event handling** for loose coupling and eventual consistency.

---

## 5. Detailed Frontend Design and Features

The app is split into layered stacks and tab-based navigation for clean user experience.

### 5.1 Entry and Authentication Flow
#### Entry Page
- Presents app branding.
- Provides Login/Register routes.
- Can skip to home if authenticated session is valid.

#### Login Page
- Email/password login.
- OAuth via Google/GitHub.
- Forgot password entry-point.
- Validation and error handling for credentials/network issues.

#### Register Page
- Account creation form.
- OAuth sign-up alternatives.
- Terms agreement and validation checks.
- Optional uniqueness/verification flow.

### 5.2 Home Page (Three Main Tabs)

#### Tab 1: Feed
- Post-card based browsing.
- Search and category filtering.
- Claim and report actions.
- Add-to-watchlist interactions.
- **Map View Toggle Alternative**:
  - An alternate rendering mode of same feed results.
  - Pins display geolocated posts on map.
  - Pin click reveals preview card with action shortcuts.

#### Tab 2: Messaging
- Conversation list with unread indicators.
- Chat interface with text and image messaging.
- Canned response quick-actions.
- Peer moderation options (block/report).

#### Tab 3: Profile
- User identity and trust score display.
- Activity metrics (posts, claims).
- Manage locations.
- Access blocked users/report history/settings.
- Logout and account deletion entry points.

### 5.3 Shared Modal Components
- Location selection popup
- Post detail modal
- Claim confirmation modal
- Filter/search modal
- Watchlist setup modal
- Report modal
- Rating/feedback modal
- Password recovery flow screens

---

## 6. Detailed Backend and Service Design

### 6.1 API Gateway
Acts as unified ingress for frontend calls. Handles routing to domain services and can enforce policy layers (auth validation, rate checks, and consistent API surface).

### 6.2 User & Location Service
Manages:
- user accounts,
- auth metadata,
- location records,
- safety operations (block/report),
- device token management.

Also enforces one-active-location rule per user via partial unique index.

### 6.3 Inventory Service
Handles:
- post lifecycle states (`Draft`, `Active`, `Claimed`, `Expired`, etc.),
- tags and image arrays,
- watchlist matching,
- expiration checks via SQL logic and timed/state-driven workflows.

### 6.4 Claim Service
Concurrency-critical domain:
- records claim requests,
- donor approvals/rejections,
- status transitions and timestamps,
- pessimistic row-level lock strategy to prevent double-approval race conditions.

### 6.5 Reputation Service
Maintains:
- user trust metrics,
- review constraints,
- score calculation and updates based on event outcomes,
- leaderboard-like aggregate behavior.

### 6.6 Messaging Service
Supports:
- conversation persistence (post-context linked),
- text/image payloads,
- unread tracking,
- automated system messages triggered by certain backend events (e.g., claim approved).

---

## 7. Database Design (Five Isolated Databases)

### 7.1 DB1: User & Location
Core entities:
- `Users`
- `Locations`
- `Blocks`
- `Reports`

Key integrity patterns:
- unique email,
- nullable password hash for OAuth accounts,
- composite primary key in blocks (`Blocker_ID`, `Blocked_ID`),
- optional report targets (user or post),
- one-active-location-per-user through partial unique index.

### 7.2 DB2: Food Inventory
Core entities:
- `Food_Posts`
- `Watchlists`

Notable design decisions:
- status-driven post lifecycle,
- arrays for `Tags` and `Image_URLs`,
- pickup location stored as plain reference integer from another service,
- trigger/function for expiration state changes.

### 7.3 DB3: Claim (Concurrency Zone)
Core entity:
- `Claims`

Key logic:
- row-level locking with `SELECT ... FOR UPDATE`,
- atomically approve one and reject others for same post,
- tracks rating status eligibility helper flag (`Is_Rated`).

### 7.4 DB4: Reputation
Core entities:
- `User_Reputation`
- `Reviews`

Notable constraints:
- one review per claim (`Claim_ID UNIQUE`),
- rating check range (1–5),
- score calculation informed by shares, claims, failures, response speed, and ratings.

### 7.5 DB5: Messaging
Core entity:
- `Messages`

Supports:
- sender/receiver contextual communication,
- optional text or image payload modes,
- read-tracking semantics for UX improvements.

---

## 8. Event-Driven Communication and Consistency Model

Because services are isolated, consistency is achieved through event exchange.

### 8.1 Example Published Events
- `UserRegistered`
- `UserBanned`
- `PostCreated`
- `PostExpired`
- `PostFailed`
- `ClaimRequested`
- `ClaimApproved`
- `ClaimRejected`
- `DonationCompleted`

### 8.2 Example Subscriptions
- Inventory listens to claim/user-related signals.
- Claim listens to post lifecycle signals.
- Reputation listens to completion/failure/approval events.
- Messaging can react to approval events for auto-message insertion.

### 8.3 Benefits
- decoupled service interactions,
- independently evolvable domains,
- resilience to temporary inter-service downtime,
- easier horizontal scaling of event-heavy subsystems.

---

## 9. User Types and Privileges

### 9.1 Guest User (Unauthenticated)
- Can access entry, login, register pages only.
- Cannot post, claim, chat, rate, or manage profile data.

### 9.2 Authenticated Donor
- Create/edit/delete (soft) posts.
- Manage post lifecycle from user actions.
- View incoming claims.
- Approve/reject claims.
- Chat with claimers.
- Manage own profile and locations.

### 9.3 Authenticated Claimer
- Browse feed via list or map.
- View post details and claim items.
- Add watchlist alerts for new nearby matches.
- Chat for pickup coordination.
- Submit rating/review in allowed time window.

### 9.4 Safety and Moderation Privileges (User-Level)
- Report inappropriate user/post behavior.
- Block abusive users.
- Track own report history.
- Unblock users through profile tools.

### 9.5 System-Controlled Privileges (Backend Logic)
- auto-expire stale active posts,
- enforce single approved claim outcome per post at a time,
- compute trust/reputation metrics,
- publish notifications/events for dependent services.

---

## 10. Modules Developed (Functional Breakdown)

### 10.1 Frontend Modules
1. Authentication stack (entry/login/register/password recovery/OAuth)
2. Feed list UI
3. Feed map-view toggle mode
4. Search/filter flow
5. Post detail and claim modal flow
6. Messaging list + chat UI with image support
7. Profile, activity, locations, trust display
8. Reporting and blocking interaction flows
9. Watchlist and rating modal UX components

### 10.2 Backend Modules
1. API gateway and route orchestration
2. User/location domain APIs and validations
3. Inventory domain lifecycle and watchers
4. Claim domain transaction-safe approval flows
5. Reputation domain aggregate updates
6. Messaging domain persistence and system prompts
7. Event producer/consumer wiring
8. Containerized service startup and configuration support

### 10.3 Database Modules
1. Five isolated normalized schemas
2. Constraints (unique/check/composite keys)
3. Partial unique index for active location control
4. Trigger/function for expiration transitions
5. Transaction locks for claim concurrency control
6. Review and rating integrity constraints

---

## 11. Member-Wise Contribution Report

### Member 1: Udit Chaudhary (2024A7PS0618G)  
**Assigned Role:** Database Architect & User Service Developer  
**Contribution Summary:**
- Designed localized ER models for all five service schemas.
- Ensured schema normalization and 3NF quality.
- Implemented User & Location service data layer and related logic.
- Contributed to identity/location consistency constraints and safety entities.

---

### Member 2: Nikunj Goyal (2024A7PS0481G)  
**Assigned Role:** Backend Expert (Claims & Inventory)  
**Contribution Summary:**
- Developed Claim service transactional logic with pessimistic locking.
- Implemented Inventory service backend flows including status transitions.
- Implemented SQL expiration logic through trigger/procedural design.
- Integrated service event behavior for claim/inventory coordination.

---

### Member 3: Arnav Goyal (2024A7PS0608G)  
**Assigned Role:** Frontend Lead (Feed & MVVM Core)  
**Contribution Summary:**
- Established React Native frontend core architecture.
- Implemented MVVM-inspired hooks and state abstractions.
- Built Feed and Claim-centric screens and interaction pipeline.
- Standardized reusable frontend patterns for scalability.

---

### Member 4: Varun Shah (2024A7PS0862G)  
**Assigned Role:** Frontend Developer (Messaging & Profiles)  
**Contribution Summary:**
- Built peer messaging interfaces and conversation UX.
- Implemented profile and trust/reputation views.
- Added account activity and user safety interactions (block/report).
- Improved user-level module cohesion and navigation consistency.

---

### Member 5: Mansij Jain (2024A7PS0644G)  
**Assigned Role:** Backend Expert (Reputation & Messaging)  
**Contribution Summary:**
- Implemented Reputation service including score and aggregate logic.
- Built leaderboard/trust computation workflows.
- Developed Messaging service backend persistence and related APIs.
- Integrated event subscriptions for reputation and messaging updates.

---

### Member 6: Sheil Maniar (2024A7PS0498G)  
**Assigned Role:** API Gateway & DevOps Lead  
**Contribution Summary:**
- Implemented and configured API Gateway access layer.
- Setup Event Bus infrastructure (RabbitMQ/Redis) for Pub/Sub communication.
- Designed Docker Compose orchestration for full-stack local deployment.
- Supported team-wide integration, environment standardization, and service startup reliability.

---

## 12. Engineering Decisions, Trade-offs, and Challenges

### 12.1 Key Decisions
- Microservice decomposition by business domain.
- Strict no-cross-DB-foreign-key policy.
- Event-driven asynchronous consistency.
- SQL-first integrity features for high-value workflows.

### 12.2 Trade-offs
- **Pros:** modularity, scale flexibility, fault isolation.
- **Cons:** eventual consistency complexity, increased operational overhead, harder distributed debugging.

### 12.3 Major Challenges
1. Handling concurrent claim approvals safely.
2. Keeping post states synchronized across services.
3. Designing reputation updates from distributed events.
4. Balancing frontend feature richness with navigation simplicity.
5. Coordinating six-member parallel development without integration drift.

---

## 13. Testing and Validation Approach

### 13.1 Backend/DB Validation
- Transactional validation for claim approval race conditions.
- Trigger/function checks for post expiry behavior.
- Constraint validation for ratings, unique identifiers, and status transitions.

### 13.2 Frontend Validation
- Screen-level validation for auth forms and claim flows.
- Navigation tests across tabs/stacks/modals.
- UI state checks for list/map mode switching and chat flows.

### 13.3 Integration Validation
- End-to-end checks for event publication/subscription paths.
- Claim approval to messaging/reputation propagation verification.
- Docker-based full-stack startup and cross-service connectivity checks.

---

## 14. Deployment and Operational Setup

The project uses Docker Compose to orchestrate:
- API gateway
- backend service containers
- database instances
- queue/event components

This setup ensures:
- reproducible local development,
- reduced environment mismatch,
- predictable service startup sequence.

---

## 15. Future Enhancements

1. Real-time websocket-based message delivery indicators.
2. Stronger abuse detection and moderation automation.
3. Geo-index optimizations for map and watchlist queries.
4. Offline-first frontend caching for weak-network conditions.
5. Admin dashboard for moderation analytics and dispute review.
6. Advanced recommendation logic (personalized feed ranking).
7. Improved observability (centralized logs, traces, metrics).

---

## 16. Conclusion

PlateMate successfully demonstrates a full-stack distributed application with meaningful database design depth, concurrency control, modular UI, and event-driven service collaboration. The architecture balances practical DBMS concepts (normalization, constraints, SQL logic, transactions) with modern software engineering practices (microservices, asynchronous messaging, containerized deployment).

Each member contributed in a clearly defined role, enabling parallel execution and cohesive integration. The resulting system is a strong academic and engineering outcome that can be extended toward production-grade readiness with incremental improvements in observability, real-time capabilities, and operational hardening.
