package pl.lodz.uni.core.controller;

public interface Notifier {
    void registerPresenter(Presenter presenter);
    void notifyPresenter();
}
