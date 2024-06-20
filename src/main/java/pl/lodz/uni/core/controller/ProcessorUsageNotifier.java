package pl.lodz.uni.core.controller;

import pl.lodz.uni.core.Presenter;
import pl.lodz.uni.core.service.IProcessorService;

public class ProcessorUsageNotifier implements Notifier {

    private final IProcessorService service;
    private Presenter presenter;

    public ProcessorUsageNotifier(IProcessorService service) {
        this.service = service;
    }

    @Override
    public void registerPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void notifyPresenter() {
        if (presenter == null) return;

        presenter.setName("CPU load:");
        double percents = service.getProcessorUsageInPercents();
        String percentsStringRepresentation = String.format("%.2f %%", percents);
        presenter.setValue(percentsStringRepresentation);
        presenter.setProgress((int) percents);
    }
}
