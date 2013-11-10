#Simple forum software with REST API

##Features so far
 * REST API
 * JAAS authentication and user management (supports LDAP)
 * Single page app design
 * Modern Bootstrap based UI
 * Markdown in posts
 * Images are inlined, when linked to
 * We try to adhere to Clean Code principals, like KISS, DRY, peer reviews etc.

##Status
Not yet fully functional, many pieces still missing for an actual useful forum.

##Demo
 * https://skweez-forum.herokuapp.com/
 * User: testUser1
 * Password: testPassword1

##Development
We use Eclipse 4.3 for development and Maven for dependency management.

###Style
We use the Eclipse auto formatter and organize imports features configured as
save actions.
These are configured in the project specific settings, you don't need to do
anything to apply them after importing the project into your workspace.

###Architecture
The forum is a REST-like application communicating via JSON. For a starting
point have a look at the Main class.

The app can be run as a standalone Java application or deployed as a WAR in a servlet container.
