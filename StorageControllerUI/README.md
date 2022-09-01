# Storage Controller UI

This project contains the web-based UI for the Storage Controller. It's a simple UI developed 
with [Mithril](https://mithril.js.org) and built with NPM. 

---
### Local Development

1. Install NPM if you don't already have it
2. run `npm install` to install all the local packages you need
3. run `npm start` to deploy the static assets and compile/deploy the JS code to ../data/web. Changes to the src 
 directory will be redeployed automatically. This does not currently pick up changes to the ./static folder currently.

If you run the controller project now (without changing the defaults), it will mount the ../data/web directory and be 
available at https://localhost:8080  