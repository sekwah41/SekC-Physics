# SekC Physics

A ragdoll physics mod for minecraft.

# SekCLib
The main physics will be handled by sekclib. SekCPhysics just allows us to add vanilla ragdolls and other types.

# Contributing
## Code
Please ensure that your commits are in the following style for PR's

https://www.conventionalcommits.org/en/v1.0.0/

Accepted tags mostly follow the Angular style and are meant to only loosely be followed.
When commits close an issue refer in the commit description in the following style (Refs #1, #2, #3)

## Types available
See the [Release please config](./release-please-config.json) for the types available.

## Using release-please-actions
If you are wanting to use the same release configuration check out the [release-please-action](https://github.com/google-github-actions/release-please-action).
You should be able to leave out the token part though if you also want testing snapshots to automatically post you will need to create a PAT and use that otherwise the actions will not trigger.
