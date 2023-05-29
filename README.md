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
      - /trust add \<player\> \<claim-name\>
    - **Remove**
      - /trust remove \<player\> \<claim-name\>
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
      - /flag add \<flag\> \<claim-name\>
      - /flags add \<flag\> \<claim-name\>
    - **Remove**
      - /flag add \<flag\> \<claim-name\>
      - /flags add \<flag\> \<claim-name\>
- claim-bounds
  - **Description:** Shows the bounds of the current claim/s
  - **Permission:** easyguard.bounds
  - **Usage:**
    - /claim-bounds
