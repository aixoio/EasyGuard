# Easy Guard
#### A Spigot and Bukkit servers plugin to make claiming land easy
## Dependencies
- WorldGuard
- WorldEdit
## Commands
- claim
  - **Description:** You can use this command to claim land
  - **Permission:** easyguard.claim
  - **Usage:** /claim \<name\> \<x\> \<y\> \<z\> \<x\> \<y\> \<z\>
- claims
  - **Description:** You can use this command to see who has claimed land
  - **Permission:** easyguard.claims
  - **Usage:** /claims
- current-claim
  - **Description:** Tells you all of the claims you are currently in
  - **Permission:** easyguard.current-claim
  - **Usage:** /current-claim
- trust
  - **Description:** Lets you trust and untrust players from your claims
  - **Permission:** easyguard.trust
  - **Usage:**
    - **Add**
      - /trust add \<player\> \<claim-name\> \<owner|member\>
    - **Remove**
      - /trust remove \<player\> \<claim-name\>
    - **List**
      - /trust list \<claim-name\>
    - **Current**
      - /trust current
- delete-claim
  - **Description:** Lets players delete claims
  - **Permission:** easyguard.delete-claim
  - **Usage:**
    - /delete-claim \<claim-name\>
    - /remove-claim \<claim-name\>
- flag
  - **Description:** Lets you trust and untrust players from your claims
  - **Permission:** easyguard.flag
  - **Usage:**
    - **List**
      - /flag list
      - /flags list
    - **Add**
      - /flag add \<flag\> \<claim-name\> \<owner|member|non-members|non-owners|everyone|none\>
      - /flags add \<flag\> \<claim-name\> \<owner|member|non-members|non-owners|everyone|none\>
    - **Remove**
      - /flag remove \<flag\> \<claim-name\> \<owner|member|non-members|non-owners|everyone|none\>
      - /flags remove \<flag\> \<claim-name\> \<owner|member|non-members|non-owners|everyone|none\>
    - **Current**
      - /flag current
      - /flags current
    - **Reset**
      - /flag reset \<flag\> \<claim-name\> \<owner|member|non-members|non-owners|everyone|none\>
      - /flags reset \<flag\> \<claim-name\> \<owner|member|non-members|non-owners|everyone|none\>
- claim-bounds
  - **Description:** Shows the bounds of the current claim/s
  - **Permission:** easyguard.bounds
  - **Usage:**
    - /claim-bounds
- get-ip
  - **Description:** Shows the ip of a player
  - **Permission:** easyguard.get-ip
  - **Usage:**
    - /get-ip
- where-claim
  - **Description:** Shows the location of a claim
  - **Permission:** easyguard.where-claim
  - **Usage:**
    - /where-claim \<claim-name\>
- claim-help
  - **Description:** Shows a info book
  - **Permission:** easyguard.info-book
  - **Usage:**
    - /claim-help
    - /eg-help
    - /easy-guard-help
    - /guard-help
- resize-claim
  - **Description:** Lets you resize a claim
  - **Permission:** easyguard.resize-claim
  - **Usage:**
    - /resize-claim <name> <x> <y> <z> <x> <y> <z>
## Permissions
- easyguard.safelist-bypass
  - **Description:** Lets players bypass the Safe-list
  - **Default:** OP
- easyguard.size-bypass
  - **Description:** Lets plays bypass the claim size limit if one is set
  - **Default:** OP
- easyguard.get-ip
  - **Description:** Lets players use /get-ip
  - **Default:** OP
- easyguard.info-book-admin:
  - **Description:** When you run /claim-help you will get the item and open the gui
  - **Default:** OP
## License
### This software is under the MIT license
MIT License

Copyright (c) 2023 aixoio

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
