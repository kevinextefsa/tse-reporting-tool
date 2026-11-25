package tse_alleleyearselector;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import config.ProxyConfig;
import global_utils.Message;
import global_utils.Warnings;
import i18n_messages.TSEMessages;
import tse_alleleyearselector.AlleleYearSelectorEnum;
import user_interface.Actions;
import user_interface.ProxySettingsDialog;

public class AlleleYearListSelectorDialog extends Dialog {

	public Shell shell;
	
	public AlleleYearListSelectorDialog(Shell parent, int style) {
		super(parent, style);
		shell = parent;
	}

	private static final Logger LOGGER = LogManager.getLogger(AlleleYearListSelectorDialog.class);
	
	/**
	 * Open the dialog
	 * @throws IOException configuration file not found
	 */
	public void open() throws IOException {
		Shell shell = new Shell(getParent(), getStyle());
		createContents(shell);
		shell.pack();
		shell.open();
	}
	
	public static void createContents(Shell shell) throws IOException {
		
		shell.setLayout(new GridLayout(2, false));
		
		Label alleleYearListLabel = new Label(shell, SWT.NONE);
		alleleYearListLabel.setText("Allele year selector:");
		
		ComboViewer alleleYearListSelector = new ComboViewer(shell);
		alleleYearListSelector.setContentProvider(new IStructuredContentProvider() {

			@Override
			public void dispose() {}

			@Override
			public void inputChanged(Viewer arg0, Object arg1, Object arg2) {}

			@Override
			public Object[] getElements(Object arg0) {
				return (AlleleYearSelectorEnum[]) arg0;
			}
		});
		
		alleleYearListSelector.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((AlleleYearSelectorEnum) element).getLabel();
			}
		});
		
		alleleYearListSelector.setInput(AlleleYearSelectorEnum.values());
		
		Button saveBtn = new Button(shell, SWT.NONE);
		saveBtn.setText("Save");
		
		alleleYearListSelector.getCombo().addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				if(alleleYearListSelector.getSelection().isEmpty())
					return;
				
				AlleleYearSelectorEnum mode = (AlleleYearSelectorEnum) ((IStructuredSelection) alleleYearListSelector.getSelection()).getFirstElement();
				
				boolean manualMode = mode == AlleleYearSelectorEnum.NEW_SELECTOR;
				
				
			}
		});
		
		
		saveBtn.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				AlleleYearSelectorEnum mode = (AlleleYearSelectorEnum) ((IStructuredSelection) alleleYearListSelector.getSelection()).getFirstElement();
				
				try {
					AlleleYearSelectorConfig config = new AlleleYearSelectorConfig();
					config.setAreaSelectorIndex(mode.getIndex());
					Message m = Warnings.create("Info", TSEMessages.get("allelelist.dialog.inforestart"));
					m.open(shell);
				} catch (IOException e) {
					LOGGER.error("There is an error upon saving the configuration ", e);
					e.printStackTrace();
				}
				
				shell.close();
				shell.dispose();
			}
		});
		
		shell.setText(TSEMessages.get("allelelist.config.item"));
		
		AlleleYearSelectorConfig config = new AlleleYearSelectorConfig();
		
		alleleYearListSelector.setSelection(new StructuredSelection(AlleleYearSelectorEnum.fromInt(config.getAreaSelectorIndexInt())));
	}
	
//	public static void save(AreaSelectorEnum enumSelected) throws IOException {
//		
//	}
	
	public static void main(String[] args) throws IOException {
		Display display = new Display();
		Shell shell = new Shell(display);
		AlleleYearListSelectorDialog dialog = new AlleleYearListSelectorDialog(shell, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
		dialog.open();
		
		while(!shell.isDisposed()) {
			display.readAndDispatch();
		}
	}
}
