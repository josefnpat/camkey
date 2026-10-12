---
applyTo: '**'

---

General:

* Do not change the AGENTS.md file unless the user tells you to.
* Avoid early returns.
* Don't use magic numbers; define them.
* Wrap function calls >120 characters by placing each argument on a new indented line, with the closing ) on its own unindented line.
* Don't worry about backwards compatability unless the user tells you to.
* Always use braces.
* Don't add comments unless you're doing something complex worth commenting.
* New classes should always be in their own file unless the user tells you otherwise.
* Run `./gradlew test` in the project when you are done with a task.
* Run `./gradlew build` in the project if you suspect the user wants to deploy and test.
* Prefer fail-fast behavior: if a null appears in a list unexpectedly, let execution throw immediately so the bug is visible.
* Do not change the structure, indentation, or style of existing if / else if / else chains unless the user explicitly asks for a control-flow refactor.
* Treat existing comments as authoritative documentation: keep every existing comment verbatim and attached to the same code block when refactoring or moving code; if code is moved, move its comments with it.
* It is always better to rework a system than it is to add hacky workarounds (with exception of database patterns that use migrations - should ask the user first here.)
* Never use git unless the user tells you to.
* If you need a temp folder, use ./tmp

Commit Messages:

* A commit message is a one line summary of all the changes, an empty line, and then a short list of all new features/bugs that were handled as a list.
* Never stage changes for the user.
* Never add code after you write a commit message for the user unless they tell you to.
* Commit messages must not mention test status or test results (e.g., no “tests pass”, “all tests green”, or pass/fail counts).