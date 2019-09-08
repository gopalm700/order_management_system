# order_management_system

## Order management system consists of two microservices.
##### OMS and Notification. Following is the architecture for the microservices.

[Architecture link](https://www.draw.io/?lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=Untitled%20Diagram.drawio#R3VrRduI2EP0aHsORLNlYjwnJ7rYn2WzLnrPNU49jC1AjLCrkAPv1lbGMsWQCIRjY8hJrZA%2FynTtXMwod1J8sPstoOn4QCeUdDySLDrrteB5E2NN%2FcsvSWEJMCstIssTYKsOA%2FaTGCIw1Ywmd1W5UQnDFpnVjLNKUxqpmi6QU8%2FptQ8Hr3zqNRtQxDOKIu9YfLFHjwhr6oLJ%2FoWw0Lr8ZAjMzicqbjWE2jhIx3zChuw7qSyFUcTVZ9CnP0StxKZ77tGV2vTBJU7XPA39%2FT8ho%2Fht%2B9p9S%2BXP6kqn7r1deULh5jXhm3tisVi1LCOZjpuhgGsX5eK7j3EE3YzXhegT1ZTSbFsAP2YLq77oxHqlUdLF1rXCNgOYOFROq5FLfUj4QGtAMba4wKsbzKgY%2BMveMN%2FBfGyMT99HadwWNvjDovAMpCB1gaKKpYoZCqrEYiTTid5X1RoosTXJQboEeVffcCzE18P1DlVoa3keZEnVwNV5y%2BZceXIEuAF5peco9dnFluF2Y7yhGy83RNyqZxoBKYyzeI1%2F829HR7yoyGdO3QDEZGckRVTtp5oZbUh4p9lpfyPFD53C8z1n%2BrnZA6%2BHawfuP0LxUQwwcWkOvgdZBW6z2HGh%2BCPky5FqoPPAo4zGdKRkpId%2FACraLlXkA4boknB875GD3xRVPrfrT%2FDJecqbxkruxei6AvX9eG6L4ZbSC%2BzFT2gs9Jqh%2BDVSPNIAKG0AlrcmsC%2BEJZHZfSfyAgplHvwmWC896o%2FMsVtv7VyGt5qnN3d12ROqOfMtPIeWOn1WM1q9zeNiwkwuPMme7B%2FqSNuqHdqprOLo7H9qoLzxkwe7SPmxgfdgW630Hvj%2B18EaZjFYx%2FioUG7IVnL%2BL58uCElulmndmKN2C9joVk4jnS77VVVCsBLs0Ovr%2BFhU4F4ihA6ILWJpc5%2F1VvrfxaDZjscZCc1Yq19xU0K7L0ydT3frt1rK7a9TmEG12HE0Nh7F9cB%2FwLPm299ct8u34QZYf2PP32k%2BOtQ%2BQExCnS0Bokwf2DmEPXTBVuIRhaMZFa0VQOa785YPlxuBQLp6LY5gEXYBI9QnqlPMbZ9%2FLQIxJlwSg%2BmBL2sIuCTemvZPys9zo2yWov0lPsIOZF08bYG%2FwhzLDdmRTrO3Yuw3bsWJ%2F8THsWdtCQA6LoW934bajtmPoNhoP4pnp8JVVclx0GwFXeeucX43yqwGVr0yXdNvrvnWnzkWWnORIo2d1IRj0Ok733XSk0VrdB%2Fc4Dm5MkWYdDHBdCbuEHLRPX3x6BfWtFBJrV9s7vUivCzD2YdDrARwSVHOLQKARxAhAjGDohd6J9XOPrqDF4y4plI6RSPWQHCkBgxB3rQMw4HZePnSJo0PcUgqWb7GBsk6HGdOb0Iopf2Q0c2VMY6DquM6UFC%2B0L7jIsygVOY43Q8a5ZYo4G%2BWYxhqzVbxyRLWK8mszMWFJwrc1xvWTt2OExDoQQ2WBuEsTvbY00XMPZ96tiZcuYL2wngbrc%2BH3CpjtCMPT9p9ek0YVtcBQrBZaRS34NxPlxNVsdTR8rW%2BA4XRRTZb1w%2BPDoHSkF1b4Kmb%2Bv7mIPVIPJoZOLuJTpiJyq7%2BBdq0td6%2Br%2F%2BfZVd9nmlLZXBC6OXzOE0BknQBenftEGh2qer9%2Bs4RInfawpN37D%2BPqjnqWn4O1UA%2Br340Ut1c%2Fv0F3%2FwE%3D)

####Technologies used:

* Spring-boot
* Java 8 (OMS)
* Kotlin 1.3 (notificator)
* JMS (Rabbit-MQ)
* Maven
* H2
* Junit 5.0
* docker-compose


### Runbook:

git clone https://github.com/gopalm700/order_management_system.git

```
cd order_management_system/oms
./mvnw clean package
cd ..
cd notification
./mvnw clean package
cd ..
docker-compose up --build
```
#### Once all the services has been started. try the followings to test Or do the same from postman. Collection is checked in.

```jshelllanguage
curl -X POST \
  http://localhost:8080/api/orders/bulk \
  -H 'Content-Type: application/json' \
  -d '{
    "orders": [
        {
            "quantity": 2,
            "orderTime": "1567962900000"
        },
        {
            "quantity": 1,
            "orderTime": "1567983600000"
        },
        {
            "quantity": 4,
            "orderTime": "1567983600000"
        }
    ]
}'
```
#####Improvements
1. Validation of orders delivery time.
2. API documentation.
2. Edge cases for order notification.
3. Implement Callback if order has been closed / rejected.
4. Implement Anomaly detector.
5. Solve Scaling issues (such as Job Lock)
6. Implement api for order status.
7. Idempotency in notification.


Troubleshooting:
<br>
Connection refused to rabbitmq?
<br>Please check if there are any services running on port 5672.
