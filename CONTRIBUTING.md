# Contributing to SmartCredentials Open Source Project

First thing first, thank you for taking the time to contribute! We are very excited to hear and learn from you.

The following is a set of guidelines for contributing to SmartCredentials project. We have put together the following guidelines to help you figure out where you can be most helpful.

## Table of Contents

1. [Code of conduct](#1-code-of-conduct)
2. [How to contribute](#2-how-to-contribute)
3. [Coding standards](#3-coding-standards)
4. [Community](#4-community)

## 1. Code of conduct

### Our Pledge

In the interest of fostering an open and welcoming environment, we as
contributors and maintainers pledge to make participation in our project and
our community a harassment-free experience for everyone, regardless of age, body
size, disability, ethnicity, sex characteristics, gender identity and expression,
level of experience, education, socio-economic status, nationality, personal
appearance, race, religion, or sexual identity and orientation.

### Our Standards

Examples of behavior that contributes to creating a positive environment
include:

* Using welcoming and inclusive language
* Being respectful of differing viewpoints and experiences
* Gracefully accepting constructive criticism
* Focusing on what is best for the community
* Showing empathy towards other community members

Examples of unacceptable behavior by participants include:

* The use of sexualized language or imagery and unwelcome sexual attention or
  advances
* Trolling, insulting/derogatory comments, and personal or political attacks
* Public or private harassment
* Publishing others' private information, such as a physical or electronic
  address, without explicit permission
* Other conduct which could reasonably be considered inappropriate in a
  professional setting

### Our Responsibilities

Project maintainers are responsible for clarifying the standards of acceptable
behavior and are expected to take appropriate and fair corrective action in
response to any instances of unacceptable behavior.

Project maintainers have the right and responsibility to remove, edit, or
reject comments, commits, code, wiki edits, issues, and other contributions
that are not aligned to this Code of Conduct, or to ban temporarily or
permanently any contributor for other behaviors that they deem inappropriate,
threatening, offensive, or harmful.

### Scope

This Code of Conduct applies within all project spaces, and it also applies when
an individual is representing the project or its community in public spaces.
Examples of representing a project or community include using an official
project e-mail address, posting via an official social media account, or acting
as an appointed representative at an online or offline event. Representation of
a project may be further defined and clarified by project maintainers.

### Enforcement

Instances of abusive, harassing, or otherwise unacceptable behavior may be
reported by contacting the project team at Joerg.Heuer@telekom.de. All
complaints will be reviewed and investigated and will result in a response that
is deemed necessary and appropriate to the circumstances. The project team is
obligated to maintain confidentiality with regard to the reporter of an incident.
Further details of specific enforcement policies may be posted separately.

Project maintainers who do not follow or enforce the Code of Conduct in good
faith may face temporary or permanent repercussions as determined by other
members of the project's leadership.

### Attribution

This Code of Conduct is adapted from the [Contributor Covenant][homepage], version 1.4,
available at https://www.contributor-covenant.org/version/1/4/code-of-conduct.html

[homepage]: https://www.contributor-covenant.org

For answers to common questions about this code of conduct, see
https://www.contributor-covenant.org/faq

## 2. How to contribute?

### Reporting bugs

This section guides you through submitting a bug report for SmartCredentials library. Following these guidelines help maintainers and the community understand your report, reproduce the behavior and find related reports.

#### How do I submit a __good__ bug report?

Bugs are tracked as [GitHub issues](https://github.com/telekom/SmartCredentials-SDK-android/issues). When you are creating a bug report, please follow the best practices listed below and use the required template:

##### Summary

A brief summary of the bug in 60 words at maximum. Make sure your summary is reflecting what the problem is and where it is the effect the bug has, the circumstances under which it happens and anything you might already have found out about the issue yourself.

##### Description

The bug description helps the developers to understand the problem. The description should contain an ordered list of the process of finding and reproducing the issue. See the example below:

1. User choses to delete a dataset
2. User receives a modal popup  warning
3. User confirms warning, but the dataset gets lost completely without a choice

##### Expected result

Describe how the application should behave on the above-mentioned steps.

##### Actual result

What is the actual result of running the above steps i.e. the bug behavior.

##### Severity & Priority

Based on the severity of the bug, a severity and priority can be set for it. A bug can be a Blocker, Critical, Major, Minor, Trivial, or a suggestion. A bug priority from 1 to 5 can be given so that the important ones are viewed first.

##### Environment

Specify the OS - and the exact version – under which the bug appeared. It is the best way to communicate how the bug can be reproduced. Without the exact platform or environment, the application may behave differently and the bug at the tester’s end may not replicate on the developer’s end.

##### Attachments

Take a Screenshot / Video of the instance of failure with proper captioning to highlight the defect and attach it to the bug report.

**Essential to have**:

- First of all, __check if the bug is already known__ (check whether or not the bug you found is already documented in the [issue tracker](https://github.com/telekom/SmartCredentials-SDK-android/issues). If it has and the issue is still open, add a comment to the existing issue instead of opening a new one. 
- Add the bug a __short and precise title__ (e.g. “Unable to log into the system when username contains foreign characters” and __not__ “User cannot work in Chinese”).
- Reproduce the bug __more than one time__ before reporting it.
- Offer __a concise but complete description__ of the issue. Include the steps to reproduce, the consequences of the bug and the expected behavior.
- __Attach relevant screenshots__, __videos__ or __log files__ that help developers to understand better the malfunction of the feature.
- If you notice multiple bugs, __file them separately__ so they can be tracked easier and do not mix the bugs.

**Nice to have**:

- Add a __Severity & Priority__ (if not sure, leave it as default or medium).
- __Follow-up__ on the bug reported and __provide comments__ when necessary.
- __Be nice__ while logging a bug and avoid using caps lock or exclamation marks even though you might be a bit annoyed of the situation.

**You are awesome if**: 

- You are already awesome for finding the bug and for reporting it.
- You put as much effort into your question as you expect to be put into its response and the remedial.

### Suggesting enhancements

This section guides you through submitting an enhancement suggestion for SmartCredentials library, including completely new features and minor improvements to existing functionality. Following thse guidelines help maintainers and the community understand your suggestion and find related suggestions.
It would be appreciated if you could classify enhancements:
*  New possible module
*  New functionalities for SmartCredentials' core
*  Enhancements to the environment, to our rules or appearance

Before creating enhancements, please check [this list](https://github.com/telekom/SmartCredentials-SDK-android/issues) to see if the enhancement has already been suggested. If it has, add a comment to the existing issue instead of opening a new one.

#### How do I suggest a __good__ enhancement suggestion?

Enhancement suggestions are tracked as [GitHub issues](https://github.com/telekom/SmartCredentials-SDK-android/issues). When you are suggesting an enhancement, please follow the best practices listed below:

Nothing is that good that it can’t be made better. We very much appreciate your input on how we can make SmartCredentials better. Whether it’s for ease of use, improved functionality or a totally new type of feature, please follow the best practices listed below and use the required template:

##### Summary

A brief summary of the improvement or new functionality in 60 words or less.

##### Description

The description should provide a clear proposition and line of thought, as we need also assess a priority according to the value of your idea. Make it short, but comprehensive enough. Please provide at least:
1.	The functionality or improvement of an existing functionality you are proposing
2.	The effect on software that uses the SmartCredentials library in terms of functionality, ease of use or improved performance, flexibility, etc.
3.	Input on - how much software will benefit, how many people will enjoy, how many hours of development time may be saved - if your idea gets implemented
4.	Refer to examples, provide URLs or other resources which will have to be taken into account or could help in the process of implementation
5.	It is totally okay to propose a technology you love – or maybe even have developed yourself – for inclusion. SmartCredentials can help technologies and technological products to make it to the market (if there is any commercial interest involved, heed the next bullet…)
6.	Consider getting involved in enhancing SmartCredentials yourself (or as a company). Adding yourself to the team behind SmartCredentials or relating to it by contributing will not only make SmartCredentials better sooner – it will also add to the priority of your idea being processed.

##### Proposed approach

Describe how the functionality should behave within SmartCredentials.

##### Changes to SmartCredentials as it is

We welcome every addition to the SmartCredential modules which usually doesn’t ask for changes to SmartCredentials itself. However, the SmartCredentials framework and our architecture might also show room for improvement. In this case please make sure to answer these questions: Does your proposal change the existing behavior of the library? Does it change APIs or need additional ones?

##### Severity & Priority

Based on the severity of the bug, a severity and priority can be set for it. A bug can be a Blocker, Critical, Major, Minor, Trivial, or a suggestion. A bug priority from 1 to 5 can be given so that the important ones are viewed first.

##### Environment

Are you proposing to port SmartCredentials or an existing module to a new OS or other environment? Please specify as exactly as you can.
There are non-technical environments we need to take into account – please let us know: Does your idea imply to use licensed code or collaboration with projects which are working under different licenses and copyright rules? Anything else (legal, financial or socially) we should know?

##### Attachments

Please provide links, documents or illustrations that help to understand your idea. The material might be shared with contributors, so make sure you have the right to share these materials.

#### Essential to have:
- First of all, check if a similar idea is already registered (Is there an easy way to register and share such ideas similarly to sharing bugs and their states?)
- Give your idea a short and precise title (e.g. “Adding heart frequency based authentication of XYZ Ltd. to SmartCredential’s authentication methods” and not “Making SmartCredentials much much better”).
- Write in good, easy-to-read English. Consider to have someone prove-read if you aren’t sure. Discuss your idea with others in the SmartCredentials forum or in your team. If you can make yourself understood to others close to you, it will be easier to formulate a few sentences and draw up the right illustrations to make us understand the beauty of your idea too!

#### You are awesome if:

- You are already awesome for getting involved.
- You certainly are awesome if you know how to do it and let us know...
- You are particularly awesome if you want to get involved in the implementation or testing.

## 3. Coding standards

If you're writing content, see the following [coding standards](https://github.com/telekom/SmartCredentials-SDK-android/blob/us_195_migrate_library/CODING-STANDARDS.md) to be sure that your content matches with the rest of the code.

## 4. Community

We are open for any contribution on the topic of credential management, authentication and user-centric identity.
Even more are we looking for partners who have an interest in adding their solutions to the list of existing modules. However, if you have good reason to think it would be good to have **someone else's solution** adapted, you might just be the person who simply does it. Just make sure you are **allowed** to handle the code or executable in question.

Discussions about the SmartCredentials library take place on this Slack [channel](https://smartcredentialssdk.slack.com/). Anybody is welcome to join these conversations.

Wherever possible, do not take these conversations to private channels, including contacting the maintainers directly. Keeping communication public means everybody can benefit and learn from the conversation.