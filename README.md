# OrderService
Структура работы

Store service собирает всю необходимую информацию для создания order (список вещей, их количество, общая сумма заказа, id юзера и его адрес).
Store service передает нам на order service собранную информацию.
Order service сохраняет информацию в таблицу order и  goods, и отдает в store service созданный order_id.
Ep на order service /create
 (http://localhost:8080/api/order/create)
Content-Type: application/json
        Body:
{
    "id": "611a678a-8d1d-4baf-a766-73e4361945c8",
    "userId": 123,
    "storeId": 456,
    "totalPrice": 100.0,
    "deliveryDatetime": "2023-09-27T10:30:00Z",
    "deliveryAddress": "123 Main St",
    "goods": [
        {
            "productId": 101,
            "goodsQuantity": 5
        },
        {
            "productId": 102,
            "goodsQuantity": 3
        }
    ]
}

На этот запрос order service возвращает в responseEntity:
{
    "orderId": 611a678a-8d1d-4baf-a766-73e4361945c8
}
HttpStatus: Created

В Order service формируется заказ, а Store service направляет orderId на оплату в payment service и получает ответ.
После успешной оплаты Store service передает в Order service статус оплаты:
        Ep на order service /payment_status?status=true
        (http://localhost:8080/api/order/payment/611a678a-8d1d-4baf-a766-73e4361945c8)

После получения положительного статуса оплаты Order service передает заказ в delivery service.
        Ep на delivery service /create
{
    "orderId": 611a678a-8d1d-4baf-a766-73e4361945c8
}
