## Tools

Here are the tools I used to build this.

* OpenCode (DeepSeek 4.1 Flash, some Big Pickle)
* Visual Studio Code
* Gradle
* MineCraft 1.21.1

## Timeline

Here is the timeline for my development.

* 2pm-3pm:
  * buy minecraft
  * get and configure opencode subscription
  * set up environment (gradle)
  * set up build environment and make test mod (neoforge, jar deployment, etc)
* 3pm-4pm: scope out project and have ai review development plan
* 4pm-5:30pm: Build out function by function with tests.
* 5:30pm-6pm: Added documentation and made demo video

## AI Usage Notes

* Minecraft uses yaw, pitch + position, but doesn't use roll. This felt like a killer feature, and I had to coax the AI to include it, and not just cache it locally in the mod.
* Generally when AI produces incorrect results, I will either:
  1. inform the AI what to fix, and update my AGENTS.md file
  2. correct it by hand, and then inform the AI to correct it.
* For this implementation, I found the output of DeepSeek 4.1 Flash to be acceptable, and did not have to correct anything by hand.