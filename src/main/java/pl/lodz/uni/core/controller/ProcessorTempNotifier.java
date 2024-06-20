package pl.lodz.uni.core.controller;

import pl.lodz.uni.core.Presenter;
import pl.lodz.uni.core.service.IProcessorService;

public class ProcessorTempNotifier implements Notifier {

    private final IProcessorService service;
    private Presenter presenter;

    public ProcessorTempNotifier(IProcessorService service) {
        this.service = service;
    }


    @Override
    public void registerPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void notifyPresenter() {
        if (presenter == null) return;

        presenter.setName("CPU temperature:");
        double temp = service.getProcessorTemp();
        presenter.setValue(String.format("%.2f C", temp));
        presenter.setProgress((int) temp);
    }
}
