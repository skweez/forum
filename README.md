#Simple forum software with REST API

##Features
 * REST API
 * LDAP authentication and user management (feature-jaas-authentication branch)
 * markdown in posts
 * single page app design
 * clean-code

##Demo
 * https://skweez-forum.herokuapp.com/
 * User: testUser1
 * Password: testPassword1

##Development
We use Eclipse 4.2 for development and Maven for dependency management.

###Style
We use the Eclipse auto formatter and organize imports features configured as
save actions.
These are configured in the project specific settings, you don't need to do
anything to apply them after importing the project into your workspace.

###Architecture
The forum is a REST-like application communicating via JSON. For a starting
point have a look at the Main class.

The forum can be run as a Java Application or a servlet can be generated.
