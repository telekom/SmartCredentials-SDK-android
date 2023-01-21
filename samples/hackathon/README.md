# Keeper
## SIM Mismatch Detection
This app uses the API to request a token matching the SIM card. It will then detect when a different SIM card is inserted, and notify the user at the first opportunity.
How events happen:
- SIM is inserted or detected for the first time
- APP uses API to determine the corresponding token
- DIFFERENT SIM is inserted
- APP uses API to determine the new token and compare
- APP tells user that a different SIM is inserted
### Usage
To run/configure the app:
- Host a php client with `php -S localhost:port` in local device (fill in other port if necessary)
- Determine the device's IP to direct the app on phone device
- Change the IP in the app config files
