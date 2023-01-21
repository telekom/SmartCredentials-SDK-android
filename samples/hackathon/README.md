# Keeper
## SIM Mismatch Detection
This app uses the API to request a token matching the SIM card. It will then detect when a different SIM card is inserted, and notify the user at the first opportunity.
How events happen:
- SIM is inserted or detected for the first time
- APP uses API to determine the corresponding token
- DIFFERENT SIM is inserted
- APP uses API to determine the new token and compare
- APP tells user that a different SIM is inserted
