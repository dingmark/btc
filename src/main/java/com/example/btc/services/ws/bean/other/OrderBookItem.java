package com.example.btc.services.ws.bean.other;

public interface OrderBookItem<T> {
    String getPrice();

    T getSize();
}
