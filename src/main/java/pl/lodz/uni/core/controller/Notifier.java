package pl.lodz.uni.core.controller;

import pl.lodz.uni.core.ProgressPresenter;

public interface Notifier {
    void registerProgressPresenter(ProgressPresenter progressPresenter);
    void notifyPresenter();
}
