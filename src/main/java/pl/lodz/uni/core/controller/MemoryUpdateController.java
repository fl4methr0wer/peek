package pl.lodz.uni.core.controller;

import pl.lodz.uni.core.ProgressPresenter;
import pl.lodz.uni.core.service.IMemoryService;

public class MemoryUpdateController implements Notifier {

    private final IMemoryService service;
    private ProgressPresenter presenter;

    public MemoryUpdateController(IMemoryService service) {
        this.service = service;
    }

    @Override
    public void registerProgressPresenter(ProgressPresenter progressPresenter) {
        this.presenter = progressPresenter;
    }

    @Override
    public void notifyPresenter() {
        if (presenter == null)
            return;
        double percents = service.getRamUsageInPercents();
        String percentsStringRepresentation = String.format("%.2f %%", percents);
        presenter.setValue(percentsStringRepresentation);
        presenter.setProgress((int) percents);
    }
}
