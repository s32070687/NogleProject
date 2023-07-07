# Nogle Project

## About this project:

### A Simple Cryptocurrency Exchanges Android App

### Please build up App according to the following description

* Android MVVM architecture (ViewModel/Repository)
* Use Retrofit + Coroutine to archive HTTP connection
* Use Navigation Component to handle page direction
* Use MediatorLiveData to combine multiple data sources
* Use Kotlin language only

### Please design UI according to the following description

* As a user, I want a Single-Activity App with BottomNavigationView that has 4 tabs A, B, C and D
* I can click tab A to view a market list with name and price on each item and sorted by name
* I can view spot/futures tabs at top of market list that I can toggle them to see list separately
* I can view a settings entrance button at top left of D page and I can click it to go to Settings page
* I can view the page name at the center of the page except A page. So I can know which page I'm viewing.

### Data source

* API path - https://api.btse.com/futures/api/inquire/initial/market
* Use parameter symbol to display name
* Use parameter future to distinguish between spot and futures
* WS path - wss://ws.btse.com/ws/futures
* Reference https://btsecom.github.io/docs/futures/en/#subscription
* Send {"op": "subscribe", "args": ["coinIndex"]} after socket connected
* Refer Appendix 1 for response format
* Use parameter type = 1 only
* Use parameter price to display price
