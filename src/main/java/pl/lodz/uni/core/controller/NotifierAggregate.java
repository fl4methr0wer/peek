package pl.lodz.uni.core.controller;

import java.util.ArrayList;
import java.util.List;

public class NotifierAggregate{
    private final List<Notifier> notifiers = new ArrayList<>();

    public NotifierAggregate() {
    }

    public void add(Notifier notifier) {
        notifiers.add(notifier);
    }

    public void notifyAllPresenters() {
        notifiers.forEach(Notifier::notifyPresenter);
    }
}
