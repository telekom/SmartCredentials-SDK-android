# 1CB Client App

**Disclaimer:
Everything described here is for demo purposes and not a product. Everything is provided as is without any warranty or promises that it continues to work.**

1CB Client App's purpose is to demonstrate how a client application can use the OneClickBusiness API. 

1CB Client App fetches an Operator Token which carries sensitive information about the subscription (SIM card) like [MSISDN](https://en.wikipedia.org/wiki/MSISDN) (telephone number), [IMSI](https://en.wikipedia.org/wiki/International_mobile_subscriber_identity), and pairwise subscription identifier (similar to [OpenId Connect](https://openid.net/wg/connect/)'s [pairwise identifier](https://openid.net/specs/openid-connect-core-1_0.html#PairwiseAlg)).

The operator token contains data that is not easily available to _normal_ Android applications.

## What is the opertor token good for?
- **number verification**: Many mobile apps ask the user for their telephone number and then verify that telephone number by having a backend send an SMS containing an one-time code or one-time link to that number. Entering the code or clicking on the link verfies the telephone number. 
The operator token verifies the telephone number without user interaction and without SMS cost.
- **provide carrier network products**: A carrier backend can use the IMSI to provide network products (e.g. [5G Slicing](https://en.wikipedia.org/wiki/5G_network_slicing) or Quality on Demand or ...) to a specific SIM-Card. 
Using the IMSI for this purpose is preferrable to using the MSISDN because there can be several SIM-cards with the same MSISDN but only one IMSI per SIM-Card.
- **provide unlinkable identifiers to 3rd parties**: The pairwise subscription identifier is an identifier for _this_ SIM-card. It is different for each party getting it provided by the carrier. E.g. Facebook gets a different identifier then Tiktok
If the user puts the Sim-Card into another mobile device the pairwise subscription identifier stays the same.
The pairwise subscribtion identifier can be used as a Generic Public Subscription Identifier (GPSI).

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

