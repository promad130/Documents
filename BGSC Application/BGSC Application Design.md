
# Application use-case:
## 1. Application Introduction

The platform is a comprehensive, event-driven application utilizing a microservices architecture, designed to serve as the digital hub for the BITS Goa sports and esports communities. It acts as a dual-purpose platform: a public-facing social and event engagement hub for students and gamers, and a robust internal workspace ("Union Page") for the core organizing committees (BGSC, BGEC, FitSoc).

By integrating external activity trackers (Strava, Steam), gamifying community participation through a points system, and providing seamless event management, the platform unifies campus recreation. Furthermore, it empowers the internal crew with advanced task management, automated communication, and role-based access controls to efficiently execute large-scale leagues and events.

## 2. Global UI/UX Frame & Navigation

The application features a consistent, context-aware navigational shell:

- **Dynamic Status Bar:**
    
    - _Center:_ Contextual Logo (e.g., BGSC on the home page, BGEC for esports events, FitSoc, or specific event logos like Offside).
        
    - _Left:_ Global Navigation/Side Drawer containing routing links to all major modules.
        
    - _Right:_ User Profile Picture (triggers the Account Actions pop-up).
        
- **Authentication States:**
    
    - _Guest View:_ Mirrors the core features of the public BGSC website, providing read-only access to public events and announcements.
        
    - _Authenticated View:_ Unlocks personalized feeds, friend systems, internal routing, and user profiles.
        

## 3. Comprehensive Use-Case Information

### Use Case 1: General Engagement & Social Feed (Home Page)

- **Centralized Announcements:** Users receive global announcements and newsletters (gaming/game dev updates) similar to Moodle/AWS broadcasting, potentially integrated with WhatsApp APIs.
    
- **Social Feed:** Users can view public posts from friends and the broader community, keeping up to date with highlight events and campus activities.
    
- **Media & Memories:** A dedicated media page captures images and clips from ongoing events, aggregating them to act as a digital "year-in-review" for the user's time on campus.
    

### Use Case 2: Identity & Matchmaking (User Profile & Player Card)

- **Dynamic Profiling:** Users maintain profiles containing basic info, contact details, and specific sports/esports interests.
    
- **External Integrations:** Profiles automatically pull activity data from Strava (physical sports) and Steam (esports).
    
- **Shareable Player Cards:** Users generate highly customizable, shareable player cards featuring:
    
    - Game/Sport-specific custom tags (e.g., "Striker", "IGL").
        
    - Animations, goal achievements, and fixed rating sections.
        
    - Social media handles.
        
- **Matchmaking & Teambuilding:** The system uses Player Cards and interest data to suggest friends, recommend teams for upcoming tournaments, and update preferences based on interaction with newsletters.
    
- **Future Roadmap:** Potential implementation of relationship status or deeper connection/dating features based on community interests.
    

### Use Case 3: Community Interaction (Friends & Challenges)

- **Friend Management:** Standard request, accept, reject, and search functionalities (filterable by mutuals, activities, and recent achievements).
    
- **Post Groups ("Instants"):** Users share momentary clips via a 3-tier story system:
    
    - _Non-Judgemental_ (Close circle)
        
    - _Close_ (Friends)
        
    - _General_ (Public/All friends)
        
- **Challenge System:** Users can challenge friends to specific sports or esports. Challenges can include physical space/time requirements or digital timelines.
    
- **Communication Hub:** Integrated DMs, custom group chats, and community servers. A specialized "Friendly Games Jam" section allows users to pitch game ideas, managed by the studio admins, while voice-call APIs facilitate live coordination.
    

### Use Case 4: Event & League Management

- **Event Lifecycle:** Users can browse Upcoming, Ongoing, and Past events, complete with highlight reels.
    
- **Registration:** Support for both solo and team-based event registrations.
    
- **Competition Tracking:** Live leaderboards, Hall of Fame archives, and team management dashboards.
    

### Use Case 5: Gamification & Store

- **Points System:** Users earn points through challenges, event participation, and platform engagement.
    
- **Storefront:** Points can be redeemed for community merch. The store also features an "Indie Game Support" section to publish and promote games developed by the campus studio or partner studios.
    

## 4. Internal Operations: The "Union Page" Workspace

A secure, internal-only module visible solely to BGSC/BGEC crew members for organizing operations.

### Task & Project Management

- **Task Creation & Hierarchy:** Support for mini-tasks, grouped tasks, and main tasks.
    
- **Quick Add vs. Strict Add:** "Quick Add" allows rapid, unassigned memory-logging via an app overlay. Standard task addition requires deadlines, assignees, and event association.
    
- **Event-Based Tasks (Pathways):** Complex tasks (e.g., "Make a 2D Game") are broken down into continuous, trackable steps. Inactive tasks are flagged as abandoned for others to pick up.
    
- **Views:** Tasks can be visualized via Gantt charts or calendar views.
    
- **Reminders:** Overlaid To-Do reminders and parallel push notifications track long-term deadlines.
    

### Team Coordination & Scheduling

- **Automated Task Chats:** Assigning users to a task automatically generates a dedicated group chat linked directly to that task.
    
- **Availability Tracking:** Core members update their free/busy days via a Calendar view to aid task leads in delegation.
    
- **Google Calendar Sync:** Two-way integration allows members to import academic schedules and export task deadlines or meeting times.
    

## 5. Administration & Role-Based Access Control

Managed via a secure "Users Page" accessible only to top-level admins. The system utilizes a 5-tier permission architecture:

|**Role**|**Access Level & Permissions**|
|---|---|
|**Founder**|**Absolute Access:** Can modify anything, including altering the Founder role itself and assigning highest-level system permissions.|
|**Coordinator**|**Full Operational Access:** Can manage events, workspaces, and users. Cannot alter Founder roles. Can manage and assign core/member roles.|
|**Core**|**Event & Workspace Management:** Full access to manage events. Role changing is restricted and requires Coordinator permission.|
|**Member (Union)**|**Restricted Internal Access:** Can edit specific event/task details _only_ if explicitly granted access or assigned to that event.|
|**User**|**Standard Access:** Public frontend access to register for events, use social features, and view content. No internal capabilities.|

### Union Page Workspace Roles (Within a Task):

- **Task Leads (Min. 2):** Full freedom to edit tasks, schedule meetings, assign members, and draft documentation.
    
- **Task OCs (Min. 2):** Can allocate sub-tasks to members, set deadlines, manage meetings, and create temporary sub-OCs (requires Lead approval).
    
- **Task Members:** The primary execution team for the assigned task.

# Pages in the Application (aka the microservices)  

Starting off with the Landing Page, the application is divided into two sections: 
- one is for when the user is not logged in, and 
- other is for when the user is logged in, which gives them the complete access to all the features.
as for which will be visible to which category of user is marked in the heading of each page and feature following the given rules:
- **Starts with F** for the ones which are visible to both, logged in and guest users
- **Starts with P** for the ones which are only visible upon login

Now the application has 6 access levels:
- Guest
- User
- Member
- Core
- Coordinator 
- Admin / Founder
The application has 5 user roles: user, member, core, coordinator, founder, and this is their admin section features:
	- Users: can access things, cannot change events and all
	- Members can edit the event details depending upon if they have the access to it or not
	- Core has all rounder access to events, but other things like role changing is permission based and required coordinator permission
	- Coordinator has all rounder access to everything , the only thing they cannot change is who is the founder 
	- And Founder has all access, they can change anything they want


## Application Frame:
We have a fixed **Status Bar**, on which:
- **in the middle** will be the logo of the thing you are looking at, for example in the homepage / landing page, the logo will be BGSC, in event's page, it'll be an array of BGSC, BGEC, FitSoc, then in particular event like an event on offside, the logo will be offside, in esport event the logo will be BGEC, etc,.
- **in the Left** we will have a Navigation Drawer / Side Drawer featuring the places one can navigate to, these include:
	- [[#Point System and Challenge page]]
	- [[#Sponsor / newsletters Page]]
	- [[#Friends Page]]
	- [[#Events Page]]
	- [[#Leaderboards]]
	- [[#Hall of Fame]]
	- [[#Store Page]]
	- [[#Media page]]
	- [[#Feedback and contact us]]
	- [[#Union Page]]
	- [[#Users Page]]
- **in the right**:
	- **If the user is logged in**, there is the user profile picture, clicking on that opens a new window [[#User-Profile Page]]
	- **if the user is logged out**, then there is a login button that takes the user to the [[#Login/Registration Page]]


## P: PopUPs
### Account Action popUp
In this popUp, it'll show two options in a dropdown menu style but the menu will be on the bottom of the screen and covers the whole screen in horizontal. 
It'll have two options:
- Edit Account, which **PopUp with a status bar in which we have that back button on the left** which leads back to the user page
	- This allows the user to edit the account detailes like:
		- Username
		- email
		- contact
		- password change (doesn;t require current password)
	- Change interest fields , opens up [[#Interest Fields popUp]] with current interest fields highlighted and all
	- Change newsletters they are subscribed to manually
	- Chagne the socials they have connected
- Account action, opens a new  **PopUp with a status bar in which we have that back button on the left** which leads back to the user page, where they can execute actions like:
	- Disable account 
	- Remove account 
	- Account information request (includes all chat history)
	- Privacy Policies and Term of conditions of use can be found here (hopefully there is something mentioned in there)

### Interest Fields popUp
This is the popUp which asks for their interest accross topics:
- Sports
	- Football
	- BasketBall
	- Cricket
	- Badminton
	- TT
- Esports
	- Valorant
	- CS
	- Tekken
	- Minecraft
	- Other esport games
- Gaming Industry 
	- Story mode games
	- Indie Game
- Game Development
	- Unity
	- Unreal
	- Indie games from scratch

### Get Started PopUp
This popUp appears when the user first Logs in the application. This is a series of pages, which are sequential in order to access. The pages in order are:
- Shows Interest Field popUp [[#Interest Fields popUp]]
- add friends (shows friends based on the selected interest fields and from the contact list)
- connect socials (connects to instagram, a contact card (WhatsApp), twitch, and discord)

includes making new friends, signing newsletters, the interest Fields popUp 

### Make announcement popUp
Its a popUp that allows admin / coordinators to make annoucements on the application, which uses Whatsapp API to automatically send it to the respective whatsapp community.

There will be as many types of annoucements as there are the subsections:
- BGEC (Visible to all)
- FitSoc (Visible to all)
- Airball (Visible to all)
- offside (Visible to all)
- PowerPlay (Visible to all)
- Around the net (Visible to all)
- Deuce (Visible to all)
- Highlight Events (Visible to all)
- Teams (Visible to all except the normal users and guests, i.e., visible to core, coordinator, and admin)

An announcement can have multiple tags, and each tag to be given the WhatsApp API of the respective Community / group on which the announcement should go.

### Add Post popUp
This popUp will open a window which is full screen, requires camera access, gallery access:
- Now if the user is logged out, then it reroutes to login page 
- If the user is logged in: 
	- It features a sequential tab form of display, which is vertically scrollable. 
	- First tab is to select which picture to take, i.e., they can choose either from camera or from the gallery 
	- Then upon clicking next, comes the next tab for:
		- adding the fields, 
			- i.e., post header, tags, description, 
		- status of: 
			- likes section: 
				- Like count being visible or not, (Cannot see who made the like for the sake of computation) 
				- The feature of likes can be disabled
			- comment section:
				- on or off, 
				- sharing allowed or not.  
				- and the comments can be made protected, i.e., only visible to the one who made the post, 
				- or public i.e., visible to all, 
				- or private , i.e., visible to all except the one who made the post.
			- post itself:
				- Post is public, i.e., will be visible to all, even the guest users
				- Post is Protected, i.e., will be visible to all except the guest
				- Post is private, i.e., will only be visible to users that are in user's friend's list
					- Users share momentary clips via a 3-tier post system:
					    - _Non-Judgemental_ (Close circle)
					    - _Close_ (Friends)
					    - _General_ (Public/All friends)
					- i.e., the user has the option to make 
        
	- And then comes the field to add background music (future feature)

### Profile Picture section

A popup with a option to do profile picture via cmera or gallery, similar to how we did in [[#Add Post popUp]]. Although, this will be a popUp which goes from bottom till 3/4th of the screen.

### New Event PopUp
Now this Section will have the following fields:
- Title
- Description
- Rule Set PDF / Link
- Award List (if any)
- A Boolean for the in-app points award as a price 
	- if true, then the price amount field
- Boolean for leaderboard requirement
- Boolean for if the event is teamed or not
- A dropdown for the event type:
	- League (Always has a leaderboard)
		- Type of event:
			- Sports League:
				- Which sport league?
					- Airball
					- Offside
					- PowerPlay
					- Around The Net
					- Deuce
			- Esport League:
				- Then which game's esport league? (String Field)
			- The General League Fields:
				- Which mode of league format ? (round robin , upper-lower bracket, direct elimination, elimination after n fails, etc,.)
				- Min participants count required to mark leaderboard as active
				- size of a team (if teamed event)
				- Parameters that affect the player's score (Will feature: `<name of the parameter: Data type (int, bool, float)>`, it'll be a map)
				- Need score normalization?
					- Lower limit on player score (cannot be neg)
					- Upper Limit on player score(cannot be more than 1000)
				- Need Auction in the league:
					  If yes, then a followup event details are needed, which will be the auction event date and details:
						  Venue
						  Time
						  Date
					  if no, then need a deadline on participation, and a follow up event which marks the start of the league:
						  Venue 
						  Time 
						  Date
	- Event:
		- if needs leaderboard:
			- Leaderboard format: (round robin , upper-lower bracket, direct elimination, elimination after n fails, etc,.)
			- Min participants count required to mark leaderboard as active
			- size of a team (if teamed)
			- Parameters that affect the player's score (Will feature: `<name of the parameter: Data type (int, bool, float)>`, it'll be a map)
			- Need score normalization?
				- Lower limit on player score (cannot be neg)
				- Upper Limit on player score(cannot be more than 1000)
		- Venue
		- Date
		- Time
		- Award (if any)
- A field for Core People who has admin access for the event
- a field for deadline of the league (i.e., the end time for matches, and depending upon the teams, it'll automatically close the event after the last match)/ event
- An event can be auction based, so that will have the same features as that of auction based League has while making it

## F: Login/Registration Page
`*` means that the field is a compulsion
`U` means the field must be unique

This is the login and registration page, the fields common in both pages:
- Forgot password
- signUp with google

Login Page Fields:
- Move to register page
- username/email `*U`
- password `*`

Registration Page fields:
- UserName `*U`
- Email `*U`
- Contact
- Password and re-enter password fields `*`

## F: Home Page

This page is accessible to all, and it has three subpages in tab form, hence it has tab navigation for these three pages:
- Introduction Page, the Landing Page:
	- has a BGSC and Its Subsection Intro
	- some one liners catchy pahses
	- What our heads have to say section:
		- This will feature a pixelated / photo graphic of heads posed in an announcement stance making an announcement, shown beside them in an thought box design used in comics. Which head / coordinator has which announcement to them is decided by who sent the announcement. 
		- It features each coordinator's latest announcement, which is fetched from the announcement page / database, with a quick link to announcement page.
		- If a coco hasn't sent any announcement, they will have a meme attached beside them.
		
- Announcements for things (The landing page)
	- General idea being. Announcement made somewhere else but everyone gets it on their app like how moodle(aws announcements) and email work tgt) 
	- Can be an API from the whatsapp community chat or not, decided by the budget
	- The coordinator and admin has the access to make a new announcement using [[#Make announcement popUp]]
	- We maintain the announcements made in past 4 months only on the page and database, and remove any older announcements made
	- We also store which coordinator / admin made which announcement
	
- General Posts that are public and belongs to their friends / strangers who have kept the post as public,  this to also have a add post icon in right bottom, which opens [[#Add Post popUp]]

Example for introduction page: https://www.behance.net/gallery/27476403/Minions-Website

## P: User-Profile Page
Replaced with login in button if not loged in
The **Main status Bar for this changes to something else**, where we have:
	- On the status bar, a back button on the left which takes back to the landing page, an account actions in the middle, which opens a [[#Account Action popUp]], profile picture on the right while opens up [[#Profile Picture section]]


The user page will have the user card like these: https://pin.it/81Wcd43Gj on the center of the page, 
Player Card / User Card:
- Username
- Avatar (2D)
- Short Description
- Sports/Esports Interest(Games)
- Animations
- Goal Achievements
- Custom Tags(Can be particular to a game/Sport like “Striker”/”Goal Scorer" in Football)
- Social Media Links / handles
- Include both customizable and fixed section (Ratings and all)
- (To be used in)Shareable Card (To post on socials/with friends)
- (To be used in)A section somewhere else to Find similar players based on cards

the userCard section it will be called, with their info below that:
- Tags given to them by their friends
- Name
- Email 
- Contact
- Interest fields (for both, sports and esport)
- Newsletter for interest fields and it’s interaction Data
- Sport activity (connected via external apps like strava)
- Esport Activity (Connected via external apps like Steam (Would need to get steam unbanned for this thou))
- Socials of the user itself

Then there will be a events suggestion section, which will show events that are upcomming and highlight events and tailor them acc to user's past expreice and interest if any while forcing them to try out new things as well. 
In those suggested events, below the event card will be a list of suggestion teams (which are public, i.e., the teams that are open for public to join) for teamed tournaments based on interest fields

Then there will be a friends suggestion section, which will show people according to contact list, or user interest, or user event activity.

Then comes the history section, ie.,  Events participated/games played/match history

Some bakchodi feature too include in future:
Looking for? (Dating feature?) (Relationship status?) (Future Implementation)[jyada pi rakhi thi ye likhte time] hehe
  
## P: Friends Page

This page will have tab navigation for 5 tabs:
- General chats and search:
	- Shows all the friends and chats of all the frinds and all
	- has a search bar to:
		- Find friend, once found, you can click on them and that shows the player card and the option to send request to the player to be friend.
		- Search for people will:
			- Mutuals show
			- Activites interested show
			- the player card of the friend show
	- Has a section / popUp triggered by the button on the right / beside the search bar to see all friend requests:
		- Accept/Reject
		- Add/remove friend
	- Chat Section (Group, Dms, Servers(aka community chat) )
	- Active Users
- Activities/Events Friends have participated in

- Recent Achievements:
	Shows wins and all for friends, you will get a list of frineds, and then the wins and all when you click on it

- Challenge Friends:
	You select a challange from the list of available challanges, and send it to a friend
	Challenges among friends (so basically we from project team or sports or esport put up a challange, the challange can be either have a physical dedicated spcae or time given if required or a general challange with a timeline with details given once accepted)

- Team up For an event:
	You select the event you want to team up in , and send the team up request to people

Share clips(instants like insta) (Would be better if we do stories instead, and people can form groups to which they can send, from the start we will have given them 3 groups to fill, non-judgementals, close, general, and non-judgemental and close they have to fill, general will have all, and they can make or remove more groups, and share instants in form of stories instead) **(Future implementation)**


  
Friends System:
- Suggest friends based on similar interest
- (a popup) Ask for new interests weekly or monthly / update it based on the interest shown in the newsletter, and change the friend suggestion
- Option to Collaborate(for a game/event) 
- Feature for active friends / teammates
- Section for friend’s participation (Live and past)
- (need to create a challenge section for this) Challenge a friend feature for both sport / esport.


## Events Page 
Will be in a Tab Navigation form for:
- Leagues (Both sport and esport)
- BGEC events
- Fitsoc
- General Events (the highlight events like on waves or sponsered events, etc,.,)

Will have filter option in each tab to see:
- Past
- Upcoming 
- Ongoing
These can be selected all together.

- FOR EVENTS, users will see:
	- Event info
	- Event registration section
		- name
		- game name
		- team captian or team member
			- if team captain then team name
			- team participation count 
			- team status:
				- open: open to all , i.e., anyone can join
				- invite only 
				- Closed
	- Event Team formation section , where they can see team invites / their own team or send out team invites to people who are open to join a team, they can search for teams, and look for teams by applying filters
	- They will have a toggle of open / closed/ invite only for events, i.e., they can be added to a team (open), they can be sent an invite to join (invite), they are closed and are not accepting any invites to a team (closed)
	- Event leaderboard once its active (automatcally covers the events without leaderboard, as they wont have that option toggeled)
	- Event status
	- Event Results
- For Leagues:
	- same things as those in event, just that there is a change in the registration section:
		- If auction Based event, then the registration field will ask for :
			  if team captain, then no worries
			  if team member, then starting cost of getting into team

Now for an auction event, we will have a modified dedicated page, which shows the ongoing on auction like who is currently being auction, the live updates, 
for team captains, a way to raise money on the application, etc,., basically it'll be an auction interface for all the team leaders, users and management of BGSC who are in for the event:
	So the management has a list of all the team leaders, and can assign money to them on the application, set a min bid bracket, and start the auction
	One the auction is turned on, the mamangement can select a player to put on stand, and the team leaders can see whoi is there on the stand and start bidding via the application, raising money in the multiples of the min bracket set by management, once a bid is made by the team leader, it is final and updated on the application with a 5 sec counter, after that 5 sec counter, the management can select the button that turns off the bid, or else the bid continues

[11:36 am, 4/6/2026] Nikunj: toh wahi nah, 
uske liye ek upper and lower limit daal dunga, baki for to accomodation of their own stupidity there is this system of new method where players if do a higher number then it ruins the chances of them being selected, and the main cost spending pool for all the team leaders would be K times sum of all the player's money which will then be given out by the OC / management
[11:36 am, 4/6/2026] Nikunj: and aisa he toh we'll add a disclaimer to help out in setting the price
[11:36 am, 4/6/2026] Nikunj: mentioning the starting cost, and variables to consider while setting price
[11:36 am, 4/6/2026] Nikunj: and using the data of what prices have been added, we will display the avg price
[11:36 am, 4/6/2026] Nikunj: deviation
[11:36 am, 4/6/2026] Nikunj: and varience in that field
[11:36 am, 4/6/2026] Nikunj: which helps in setting up the price
[11:36 am, 4/6/2026] Nikunj: matlab lets say ki A,B,C,D are going as pllayers
[11:36 am, 4/6/2026] Nikunj: and A : 1500
B: 2000
C: 1500
D: 1000
[11:36 am, 4/6/2026] Nikunj: so the totoal cost pool is 6000
so now we cannot just hand out 6000 as the total pool
[11:36 am, 4/6/2026] Nikunj: we will need to hand out 12000 or 18000 so that there is room for auction for team leaders
[11:36 am, 4/6/2026] Nikunj: so that K is the multiplier for
[11:36 am, 4/6/2026] Nikunj: the ones who auction for the players
[11:49 am, 4/6/2026] Rohan Agrawal DoPy 2024: form mein we’ll ask players to fill their price with all the data given
[11:49 am, 4/6/2026] Rohan Agrawal DoPy 2024: if it’s too much chaos and doesn’t seem to work then oc can change prices accordingly
cool, 
but then there will be counter as to the number of times it can be done, 
it can only be done for 3/7th the amt of players
[11:36 am, 4/6/2026] Nikunj: **A new feature to add in the auction page**
This is a fantastic application of game economy mechanics to a real-world event. Calculating the total management purse as $\text{Pool} = K \times \sum \text{Base Prices}$ is a highly effective way to ensure market liquidity while scaling perfectly with whatever the players decide to input.

To answer your question: **Yes, this exact system exists in the real world, most famously in the Indian Premier League (IPL) auction.**

In the IPL, players (along with their agents or national boards) choose their own base price brackets before the auction begins, which typically range from ₹20 lakh to ₹2 crore [cite: 1.1.3]. Because humans are involved, the exact "stupidity" and market inefficiencies you are trying to prevent happen every single year.

Here is how the dynamic plays out when players set their own entry costs:

 The "High Base Price" Trap

When a player overestimates their worth and sets a high entry cost, it frequently backfires:

- **The Unsold Pool:** Players who set a high base price often go completely unsold because franchises feel they are simply too costly right out of the gate [cite: 1.1.3]. Team captains come in with strict budgets; if a mediocre player demands a premium entry point, teams will simply skip them to conserve funds for guaranteed stars or cheaper alternatives [cite: 1.1.3].
    
- **System Inefficiency:** Research on IPL mega auctions highlights that allowing players to specify their own base price creates massive inefficiencies, sometimes dragging the auction process out for days [cite: 1.2.3]. The problem is so prevalent that researchers have proposed machine learning algorithms to automatically cluster players and assign base prices to fix the human error [cite: 1.2.3].
    

 The "Low Base Price" Advantage

Counterintuitively, a low base price is often the smartest strategy for a highly skilled player:

- **Higher Probability of Selection:** Players with lower base prices generally have a much better chance of being picked up by a team than those with inflated base prices [cite: 1.3.1].
    
- **Triggering Bidding Wars:** A low entry cost removes the financial risk for the buyer, getting multiple team leaders to raise their paddles. Once the bidding starts, ego, team strategy, and competitive tension take over. A good player with a low base price will almost always get pushed up to their true market value (or higher) because multiple captains get emotionally invested in the bidding war.
    

 Your UI/UX Solution

Given your focus on robust system architecture for the campus esports and sports communities, your approach to mitigating this user error is spot on.

Providing a disclaimer and live statistics (average, deviation, and variance) is a brilliant way to gently correct player behavior. If player 'A' sees the average base price is 1000, but they are about to type in 2500, the UI naturally forces them to reconsider if they are actually worth that premium. Meanwhile, your $K$ multiplier ensures that even if everyone sets their price at 1500, the team leaders have enough of a buffer to actually fight over the players they want.

 **Added Feature: The OC Override Quota (Chaos Failsafe)**

This feature acts as an emergency valve for the management team. If the player base ignores the UI data (average, variance, deviation) and submits highly unrealistic base prices, the OC can step in to manually correct the market—but only up to a strict mathematical limit.

**How it works in the application flow:**

1. **The Data-Driven Form:** Players fill out their desired entry price in a form that actively displays the community average, variance, and deviation to encourage realistic pricing.
    
2. **The "Chaos" Evaluation:** Once all submissions are in, the system flags outliers. The OC reviews the overall pricing pool to determine if the economy is broken (e.g., too many players demanding maximum price).
    
3. **The 3/7ths Quota:** To fix the market without completely stripping the players of their choices, the OC is granted the power to manually overwrite player base prices. However, the system hard-caps this ability: **the OC can only alter the prices of exactly 3/7ths of the total registered player pool.**

If the user is a core, and is assinged to that event, then they will see another option to add memebers to the event, which will then give members a edit access to the event 
The people who have edit access to the event will have access to a section in that event's page where they can edit , delete, change or update details/ stats of any team, add new matches to the leagues by clicking on the visual graph of the league, can add stats of a match, update result of a match.

A event will be automatically closed after reaching the deadline / after the last match in the league.

If the user is Admin / coordinator:

- Now, there is a Button on the bottom right, which will only be visible to admin / coordinator, This opens a new panel with a custom status bar  [[#New Event PopUp]], which only has a back button on the left and nothing else, and that back button takes back to the events page. If the user is admin/coordinator, a button to add events on bottom right:
	This button allows them to add events. 
	On the Broad category, there are two categories:
	- Ones that need a leaderboard
		- For the ones that do need a leaderboard, there are two more categories:
			- Leagues
				- Leagues that need an auction (lets call it ALL (Auction Leaderboard League))
				- Leagues that don't need an auction (Let's call it DLL (Direct Leaderboard League))
			- Events (Lets call it LE (Leaderboard Event))
	- ones that don't need a leaderboard (Lets call it DE (Direct Event))

## Leaderboards

So this is a sperate secion, here you can see all the events that are active, filter them accordint to tags or participation and check out their leaderboard. 
here if you are a participant of an event and the event permits the leaderboard abuse, then you can invest you points in the leaderboard and improve your rank.

## Point System and Challenge page
- Points System
	- The users each are given points, now the events have points awarded to users, these can be spent in the store or leaderboard of any event.
- Challenge system
	- This is a page where we have challanges of various kinds. Challanges can be be various domains, will have user team limit to it, time limit to it, will have a resource section to help out a bit, an award, and distributed across various levels, with a legend level giving an entry to the hall of fame.
	- so basically we from project team or sports or esport put up a challange, the challange can be either have a physical dedicated spcae or time given if required or a general challange with a timeline with details given once accepted

## Sponsor / newsletters Page

- Sponsor ads/stuff/links in graceful way
	- The sponsers for the past events are shwon here, and each spons given the gallery for their spons event here, the gallery is in the horizontal scroll manner, and some detail for that sponser on the page
- newsletter (updates on the world of gaming and game dev)



## Hall of Fame
Just a asthetic collection of all the winners of leagues / highlight events or other things

## Store Page

- Store to redeem points to get merch and other things
- indie game support where we publish games made by our studio (can be made into a second app once we get enough marketing and people onboard for this from other indie studios in india)

- friendly games jam section, i.e, people can post up their game ideas under the dev section, and the idea board implementation of the thing, The main handler of that section is the studio, so studio has the admin / founder perms to that section    
- Friendly gaming section: we can integrate a third party over app displays i.e., people can connect over calls

## Media page

- Media Page that has media of everything, sewgreegated ,
- plan the UI layout of it, keep it dynamic 

## Feedback and contact us 

- Feedback (We would ignore constructive ones, jk) / Contact Us
- With past coco and their description on the application

## Union Page

- Internal Team section that is visible only to the members that are part of the BGSC team, it’ll have these features:  


Abhi tak this is what we need to add in the club member field: 

- way to track which days are free    
- way to add timelines of tasks
- Multiple tasks views (gantt charts, etc)
- way to add tasks, 
- way to make groups and assign tasks to  groups / individual members, 
- way to add priority to current day task 
- way to make tasks public / private 
- way to quick add tasks
- Quick add vs add tasks:
	- Usme the diff is that quick add can be via an overlay of the application, and the quick add goes to unassigned category, they are for quick memory which then needs to be refined and assigned and all later on. Think of them as a way to instantly do stuff without filling in details or due diligence. 
	- So you cannot add tasks that doesn;t have a deadline or isn;t assigned to a team / individual and a task that is not a part of an upcoming event or ongoing thing .
- way to categorise tasks 
- way to start discussions threads on tasks
- way to group tasks into a bigger task 
- way to add updates to tasks
- way to have mini tasks
- add Day Notes
- Add Notes feature
- add event based tasks, ime., tasks with deadline but a pathway to complete, it starts at one place and ends in steps, for example making a game, so task will look like "make a 2d game" and it has a deadline, that Make a 2D game is the main end deadline goal, hence task is given steps to achieve, those steps appear unplanned and needs to be updated regularly, so the task is counted as active if it is being updated regularly, if not then it is shifted to fail or abandoned for others to pickup and all
- add ToDo reminders for things that are in long run deadlines but are currently ongoing. These reminders overlay on other apps and display as soon as you open the application. Or make notifications system along with it running parallelly (Con: app has to have run in background perm)
- Event task:
	- A task that is not a task, but an event that is happening, this covers who all will be in the event for management and other things
	- This task will feature that Event based task key points:
		- add event based tasks, ime., tasks with deadline but a pathway to complete, it starts at one place and ends in steps, for example making a game, so task will look like "make a 2d game" and it has a deadline, that Make a 2D game is the main end deadline goal, hence task is given steps …… (Yes this one)
- Add a chat feature for the workspace separately, with groups that are automatically formed as the tasks are grouped together, aka the chat for a task opens via that task itself (aka your task becomes a group, tumahara task tumhe msg bhej rha he BOOMMMM)
- Add a calendar view for crew / core to add their free / busy days
- This will then be used to assign tasks and all by the task leads and all
- Add a connection with google calendar so that people can add meeting and schedule tasks deadlines in the calendar, they can also use this then to import or export their acads and other timings to google calender
- We need to add three permission levels:
	- Task leads:
		- Ones that can edit the tasks with full freedom, i.e., they can add meeting timings to all and all, can allot tasks to others, can make task docs
		- There has to be at least 2 of these 
    - Task OCs:
		- Ones that can allot things to peeps who are there in the task, set deadlines for all, edit meeting details / set meeting details, make / remove temporary new Task OCs (which requires Tasks Leads perms)
		- There has to be at least 2 of these as well
    - Task members:
		- These are the pure worker bees of a task
    

## Users Page


- Now, which member is the coordinator that can be set by either from previous coordinators or via backend  (not recommended)., hence we will have a section that is visible only to the coordinators / developers of the application called users, it will be a complete list of users who have access to the application, the coordinators / core can search for people via their email id and from there, they can change their status to user, member (the bgsc crew), core (bgsc core), Coordinator (BGSC current coco), founders(the main project team that started this)    
- 