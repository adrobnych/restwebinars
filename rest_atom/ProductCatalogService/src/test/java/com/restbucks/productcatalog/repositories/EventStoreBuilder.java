package com.restbucks.productcatalog.repositories;

import static com.restbucks.productcatalog.domain.ProductBuilder.product;
import static com.restbucks.productcatalog.domain.PromotionBuilder.promotion;

import com.restbucks.productcatalog.repositories.EventStore;

public class EventStoreBuilder {
    public static EventStoreBuilder eventStore() {
        return new EventStoreBuilder();
    }
    
    private EventStoreBuilder(){}
    
    public EventStoreBuilder withRandomEvents(int numberOfEvents) {
        for(int i = 0; i < numberOfEvents; i ++) {
            if(System.currentTimeMillis() % 2 == 0) {
                EventStore.current().store(product().createdAtRandomRecentDate().build());
            } else {
                EventStore.current().store(promotion().createdAtRandomRecentDate().build());
            }
        }
        return this;
    }
}
