package pl.lodz.uni.core.controller;

import pl.lodz.uni.core.Presenter;

public interface Notifier {
    void registerPresenter(Presenter presenter);
    void notifyPresenter();
}
