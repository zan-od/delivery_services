package zan.delivery_services.desktop.gui;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

import zan.delivery_services.delivery_service.model.DeliveryService;

public class LoadDataWorker extends SwingWorker<Void, LoadDataWorker.ProgressData> {

	private DeliveryService service;
	private ProgressMonitor progressMonitor;

	public LoadDataWorker(DeliveryService service, ProgressMonitor progressMonitor) {
		this.service = service;
		//this.service.setWorker(this);

		this.progressMonitor = progressMonitor;
	}

	public void updateProgress(int progress, String description) {
		ProgressData current = new ProgressData(progress, description);
		setProgress(progress);
		publish(current);
	}

	@Override
	protected Void doInBackground() throws Exception {

		//service.connect();

		/*
		 * for (int i = 1; i <= 100; i++) { if (isCancelled()) { break; }
		 * 
		 * Thread.sleep(100);
		 * 
		 * int progress = i;
		 * 
		 * ProgressData current = new ProgressData(progress, "test: " +
		 * progress); setProgress(i); publish(current); }
		 */

		return null;
	}

	@Override
	public void process(List<ProgressData> data) {
		if (isCancelled()) {
			return;
		}

		ProgressData update = new ProgressData();
		for (ProgressData d : data) {
			if (d.getProgress() > update.getProgress()) {
				update = d;
			}
		}

		if (update.getProgress() < 100) {
			progressMonitor.setNote(update.getDescription());
		} else {
			progressMonitor.setNote("done!");
		}
	}

	@Override
	public void done() {
		try {
			Void result = get();
			System.out.println(result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CancellationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		progressMonitor.setProgress(0);
	}

	class ProgressData {
		private int progress;
		private String description;

		public ProgressData() {
		}

		public ProgressData(int progress, String description) {
			this.progress = progress;
			this.description = description;
		}

		public int getProgress() {
			return progress;
		}

		public void setProgress(int progress) {
			this.progress = progress;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	}
}
