package pl.lodz.uni.core.controller;

import pl.lodz.uni.core.Presenter;
import pl.lodz.uni.core.service.IMemoryService;

public class MemoryUpdateNotifier implements Notifier {

    private final IMemoryService service;
    private Presenter presenter;

    public MemoryUpdateNotifier(IMemoryService service) {
        this.service = service;
    }

    @Override
    public void registerPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void notifyPresenter() {
        if (presenter == null) return;

        presenter.setName("RAM:");
        double percents = service.getRamUsageInPercents();
        String percentsStringRepresentation = String.format("%.2f %%", percents);
        presenter.setValue(percentsStringRepresentation);
        presenter.setProgress((int) percents);
    }
}
