# Working with the repository

Fork the entire repository. Make all changes in your local repository first. Do not push tags. Be aware of multi source
repositories, especially when submitting patches which modify build files.

## Submitting patches
 
Use Pull Requests. Name of your branch does not matter as long as it doesn't collide with the reserved branch patterns
as described in this document.

A good commit message explains what changed and why. It is fine to have the message in either past or current tense. If
submitting a multi commit Pull Request, only the first commit needs to follow this rule. Be sensible though, one word
commits are not beneficial to anyone.

It is up to your personal discretion whether you rebase/squash your branch before creating a Pull Request.

When resolving a GitHub issue, edit the Pull Request message to contain the trigger keyword (*see [Closing issues via commit messages](https://help.github.com/articles/closing-issues-via-commit-messages/)*).
Do not include the issue reference directly to the actual commit message.

## Legal gubbins

By submitting a path you indicate that you are happy and agree to the license terms of the project. For the busy people, 
see [tl;dr Legal page for MIT](https://tldrlegal.com/license/mit-license)

It is your responsibility to also check that the additional dependencies you submit are MIT and open source compatible.

## Reviewing Pull Requests

Allow the build automation (if any) to verify the Pull Request before merging. Other review policies should be agreed
by the people working on the project, but it is generally advisable to have at least some kind of peer review process
whenever possible, even in two person teams.