#Simple Forum software with REST API

##Development
We use Eclipse 4.2 for development.

###Style
We use the Eclipse auto formatter and organize imports features configured as
save actions.
These are configured in the project specific settings, you don't need to do
anything to apply them after importing the project into your workspace.

###Architecture
The forum is a REST-like application communicating via JSON. For a starting
point have a look at the ForumServer class.