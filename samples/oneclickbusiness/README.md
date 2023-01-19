# 1CB Client App

1CB Client App's purpose is to demonstrate how a client application can use the OneClickBusiness API. 

1CB Client App fetches an Operator Token which carries sensitive information about the subscription (SIM card) like MSISDN, imsi, and pairwise subscription identifier.

For demo purposes this application decrypts the Operator Token and thus the source code contains the private key.
In the usual OneClickBusiness flow the Operator Token is sent to some backend and is decrypted there.

For this mobile application to work there needs to be an app, SmartAgent, installed on the same device that has carrier-privileges.
SmartAgent does EAP-AKA to authenticate the Deutsche Telekom SIM-card and afterwards fetches the Operator Token from Deutsche Telekom's Entitlement Server.

The 1CB Client App was created for the T Phone Hackathon, see https://www.meetup.com/t-developers-in-berlin/events/290281723/.

Deutsche Telekom does not make any promises that 1CB Client and SmartAgent continue to work after the Hackathon.

## Preconditions for 1CB Client to work

- An Android phone with carrier-privileges (Pixel 5 or newer, Samsung Phone with Android 12, T Phone)
- SmartAgent being installed
- A clientId being configured at Deutsche Telekom Entitlement Server

## Installation

For the 1CB Client App flow to be executed correctly, it is necessary for the device (host) to have
a Carrier Privileged app already installed which implements the OneClickBusiness SDK and can provide a JWE Token through it.

## Operator Token Variants

### scope imsi
The Operator Token contains the imsi (international mobile subscription identifier) and the msisdn (telephone number)

### scope psi
The Operator Token contains the imsi, the msisdn and the pairwise subscription identifier

The pairwise subscription identifier stays the same if the SIM is put into another phone.
It is different for different clientIds - that is why it is called pairwise.


## Authors and acknowledgment
Main contributors to the vision, the architecture, and the code of 1CB Client App
were: Axel Nennker, Marius Olenici, Teodor Ganga, Larisa Suciu.

