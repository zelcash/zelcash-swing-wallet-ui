package com.cabecinha84.zelcashui;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import com.vaklinov.zcashui.Log;
import com.vaklinov.zcashui.OSUtil;

public class ZelCashUI {

	private static final String BUTTON_PROPERTY_COLOR = "button.background.color";
	private static final String BUTTON_SELECT_PROPERTY_COLOR = "button.select.background.color";
	private static final String CHECKBOX_PROPERTY_COLOR = "checkbox.background.color";
	private static final String CHECKBOX_SELECT_PROPERTY_COLOR = "checkbox.select.background.color";
	private static final String COMBOBOX_PROPERTY_COLOR = "combobox.background.color";
	private static final String CONTAINER_PROPERTY_COLOR = "container.background.color";
	private static final String DIALOG_PROPERTY_COLOR = "dialog.background.color";
	private static final String FILECHOOSER_PROPERTY_COLOR = "filechooser.background.color";
	private static final String FRAME_PROPERTY_COLOR = "frame.background.color";
	private static final String LIST_PROPERTY_COLOR = "list.background.color";
	private static final String MENU_PROPERTY_COLOR = "menu.background.color";
	private static final String MENU_SELECTION_PROPERTY_COLOR = "menu.selection.background.color";
	private static final String MENUBAR_PROPERTY_COLOR = "menubar.background.color";
	private static final String MENUITEM_PROPERTY_COLOR = "menuitem.background.color";
	private static final String MENUITEM_SELECTION_PROPERTY_COLOR = "menuitem.selection.background.color";
	private static final String PANEL_PROPERTY_COLOR = "panel.background.color";
	private static final String PASSWORDFIELD_PROPERTY_COLOR = "passwordfield.background.color";
	private static final String POPUPMENU_PROPERTY_COLOR = "popupmenu.background.color";
	private static final String PRESENTATIONPANEL_PROPERTY_COLOR = "presentationpanel.background.color";
	private static final String PRESENTATIONPANEL_BORDER_PROPERTY_COLOR = "presentationpanel.border.color";
	private static final String PROGRESSBAR_PROPERTY_COLOR = "progressbar.background.color";
	private static final String PROGRESSBAR_FOREGROUND_PROPERTY_COLOR = "progressbar.foregroundcolor";
	private static final String RADIOBUTTON_PROPERTY_COLOR = "radiobutton.background.color";
	private static final String SCROLLBAR_PROPERTY_COLOR = "scrollbar.background.color";
	private static final String SCROLLBAR_THUMB_PROPERTY_COLOR = "scrollbar.thumb.background.color";
	private static final String SCROLLBAR_FOREGROUND_PROPERTY_COLOR = "scrollbar.foreground.color";
	private static final String SCROLLPANE_PROPERTY_COLOR = "scrollpane.background.color";
	private static final String STARTUP_PROPERTY_COLOR = "startup.background.color";
	private static final String SPLITPANE_PROPERTY_COLOR = "splitpane.background.color";
	private static final String TABBEDPANE_PROPERTY_COLOR = "tabbedpane.background.color";
	private static final String TABBEDPANE_UNSELECTED_PROPERTY_COLOR = "tabbedPane.unselected.background.color";
	private static final String TABLE_PROPERTY_COLOR = "table.background.color";
	private static final String TABLE_HEADER_PROPERTY_COLOR = "table.header.background.color";
	private static final String TEXTAREA_PROPERTY_COLOR = "textarea.background.color";
	private static final String TEXTFIELD_PROPERTY_COLOR = "textfield.background.color";
	private static final String TEXTPANE_PROPERTY_COLOR = "textpane.background.color";
	private static final String TOOLTIP_PROPERTY_COLOR = "tooltip.background.color";
	private static final String VIEWPORT_PROPERTY_COLOR = "viewport.background.color";
	private static final String CURRENCY = "currency";

	private static final String DEFAULT_COLOR = "#ffffff";
	private static final String DEFAULT_CURRENCY = "USD";
	
	public static Color button;
	public static Color buttonSelect;
	public static Color checkbox;
	public static Color checkboxSelect;
	public static Color combobox;
	public static Color container;
	public static Color dialog;
	public static Color filechooser;
	public static Color frame;
	public static Color list;
	public static Color menu;
	public static Color menuSelection;
	public static Color menubar;
	public static Color menuitem;
	public static Color menuitemSelection;
	public static Color panel;
	public static Color passwordfield;
	public static Color popupmenu;
	public static Color presentationpanel;
	public static Color presentationpanelBorder;
	public static Color progressbar;
	public static Color progressbarForeground;
	public static Color radiobutton;
	public static Color scrollbar;
	public static Color scrollbarForeground;
	public static Color scrollbarThumb;
	public static Color scrollpane;
	public static Color splitpane;
	public static Color startup;
	public static Color tabbedpane;
	public static Color tabbedpaneUnselected;
	public static Color table;
	public static Color tableHeader;
	public static Color textarea;
	public static Color textfield;
	public static Color textpane;
	public static Color tooltip;
	public static Color viewport;
	public static String currency;

	
	
	public ZelCashUI() {
		Log.info("Loading ZelCashUI");
		LoadZelCashUIFile();
		javax.swing.UIManager.put("ScrollBar.background", ZelCashUI.scrollbar);
    	javax.swing.UIManager.put("ScrollPane.background", ZelCashUI.scrollpane);
    	javax.swing.UIManager.put("SplitPane.background", ZelCashUI.splitpane);
    	javax.swing.UIManager.put("TabbedPane.unselectedTabBackground", ZelCashUI.tabbedpaneUnselected);
    	javax.swing.UIManager.put("Viewport.background", ZelCashUI.viewport);
    	javax.swing.UIManager.put("ToolTip.background", ZelCashUI.tooltip);
    	
    	javax.swing.UIManager.put("Menu.selectionBackground", ZelCashUI.menuSelection);
    	javax.swing.UIManager.put("MenuItem.selectionBackground", ZelCashUI.menuitemSelection);
    	javax.swing.UIManager.put("Button.select", ZelCashUI.buttonSelect);
    	javax.swing.UIManager.put("CheckBox.select", ZelCashUI.checkboxSelect);
    	javax.swing.UIManager.put("ScrollBar.thumb", ZelCashUI.scrollbarThumb);
		javax.swing.UIManager.put("ScrollBar.foreground", ZelCashUI.scrollbarForeground);
		javax.swing.UIManager.put("ScrollBar.background", ZelCashUI.scrollbar);
		Log.info("Finished loading ZelCashUI");
	}

	private void LoadZelCashUIFile() {
		try {
			
			String settingsDir = OSUtil.getSettingsDirectory();
			File zelcashConf = new File(settingsDir + File.separator + "zelcash_ui.properties");
			if (!zelcashConf.exists())
			{
				Log.warning("Could not find file: {0} , will create a new one from default!", zelcashConf.getAbsolutePath());
				Copy(getClass().getResourceAsStream("/ui/zelcash_ui.properties"),zelcashConf.getAbsolutePath());
				zelcashConf = new File(settingsDir + File.separator + "zelcash_ui.properties");
				
			} 
			
			Log.info("File zelcash_ui.properties found");
			Properties confProps = new Properties();
			FileInputStream fis = null;
			try
			{
				Log.info("Lets parse all the ui settings");
				fis = new FileInputStream(zelcashConf);
				confProps.load(fis);
				button = Color.decode(confProps.getProperty(BUTTON_PROPERTY_COLOR)!= null? confProps.getProperty(BUTTON_PROPERTY_COLOR).trim():DEFAULT_COLOR);
				buttonSelect = Color.decode(confProps.getProperty(BUTTON_SELECT_PROPERTY_COLOR)!= null? confProps.getProperty(BUTTON_SELECT_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				checkbox = Color.decode(confProps.getProperty(CHECKBOX_PROPERTY_COLOR)!= null? confProps.getProperty(CHECKBOX_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				checkboxSelect = Color.decode(confProps.getProperty(CHECKBOX_SELECT_PROPERTY_COLOR)!= null? confProps.getProperty(CHECKBOX_SELECT_PROPERTY_COLOR).trim():DEFAULT_COLOR);
				combobox = Color.decode(confProps.getProperty(COMBOBOX_PROPERTY_COLOR)!= null? confProps.getProperty(COMBOBOX_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				container = Color.decode(confProps.getProperty(CONTAINER_PROPERTY_COLOR)!= null? confProps.getProperty(CONTAINER_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				dialog = Color.decode(confProps.getProperty(DIALOG_PROPERTY_COLOR)!= null? confProps.getProperty(DIALOG_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				filechooser = Color.decode(confProps.getProperty(FILECHOOSER_PROPERTY_COLOR)!= null? confProps.getProperty(FILECHOOSER_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				frame = Color.decode(confProps.getProperty(FRAME_PROPERTY_COLOR)!= null? confProps.getProperty(FRAME_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				list = Color.decode(confProps.getProperty(LIST_PROPERTY_COLOR)!= null? confProps.getProperty(LIST_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				menu = Color.decode(confProps.getProperty(MENU_PROPERTY_COLOR)!= null? confProps.getProperty(MENU_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				menuSelection = Color.decode(confProps.getProperty(MENU_SELECTION_PROPERTY_COLOR)!= null? confProps.getProperty(MENU_SELECTION_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				menubar = Color.decode(confProps.getProperty(MENUBAR_PROPERTY_COLOR)!= null? confProps.getProperty(MENUBAR_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				menuitem = Color.decode(confProps.getProperty(MENUITEM_PROPERTY_COLOR)!= null? confProps.getProperty(MENUITEM_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				menuitemSelection = Color.decode(confProps.getProperty(MENUITEM_SELECTION_PROPERTY_COLOR)!= null? confProps.getProperty(MENUITEM_SELECTION_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				panel = Color.decode(confProps.getProperty(PANEL_PROPERTY_COLOR)!= null? confProps.getProperty(PANEL_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				passwordfield = Color.decode(confProps.getProperty(PASSWORDFIELD_PROPERTY_COLOR)!= null? confProps.getProperty(PASSWORDFIELD_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				popupmenu = Color.decode(confProps.getProperty(POPUPMENU_PROPERTY_COLOR)!= null? confProps.getProperty(POPUPMENU_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				presentationpanel = Color.decode(confProps.getProperty(PRESENTATIONPANEL_PROPERTY_COLOR)!= null? confProps.getProperty(PRESENTATIONPANEL_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				presentationpanelBorder = Color.decode(confProps.getProperty(PRESENTATIONPANEL_BORDER_PROPERTY_COLOR)!= null? confProps.getProperty(PRESENTATIONPANEL_BORDER_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				progressbar = Color.decode(confProps.getProperty(PROGRESSBAR_PROPERTY_COLOR)!= null? confProps.getProperty(PROGRESSBAR_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				progressbarForeground = Color.decode(confProps.getProperty(PROGRESSBAR_FOREGROUND_PROPERTY_COLOR)!= null? confProps.getProperty(PROGRESSBAR_FOREGROUND_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				radiobutton = Color.decode(confProps.getProperty(RADIOBUTTON_PROPERTY_COLOR)!= null? confProps.getProperty(RADIOBUTTON_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				scrollbar = Color.decode(confProps.getProperty(SCROLLBAR_PROPERTY_COLOR)!= null? confProps.getProperty(SCROLLBAR_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				scrollbarForeground = Color.decode(confProps.getProperty(SCROLLBAR_FOREGROUND_PROPERTY_COLOR)!= null? confProps.getProperty(SCROLLBAR_FOREGROUND_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				scrollbarThumb = Color.decode(confProps.getProperty(SCROLLBAR_THUMB_PROPERTY_COLOR)!= null? confProps.getProperty(SCROLLBAR_THUMB_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				scrollpane = Color.decode(confProps.getProperty(SCROLLPANE_PROPERTY_COLOR)!= null? confProps.getProperty(SCROLLPANE_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				splitpane = Color.decode(confProps.getProperty(SPLITPANE_PROPERTY_COLOR)!= null? confProps.getProperty(SPLITPANE_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				startup = Color.decode(confProps.getProperty(STARTUP_PROPERTY_COLOR)!= null? confProps.getProperty(STARTUP_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				tabbedpane = Color.decode(confProps.getProperty(TABBEDPANE_PROPERTY_COLOR)!= null? confProps.getProperty(TABBEDPANE_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				tabbedpaneUnselected = Color.decode(confProps.getProperty(TABBEDPANE_UNSELECTED_PROPERTY_COLOR)!= null? confProps.getProperty(TABBEDPANE_UNSELECTED_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				table = Color.decode(confProps.getProperty(TABLE_PROPERTY_COLOR)!= null? confProps.getProperty(TABLE_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				tableHeader = Color.decode(confProps.getProperty(TABLE_HEADER_PROPERTY_COLOR)!= null? confProps.getProperty(TABLE_HEADER_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				textarea = Color.decode(confProps.getProperty(TEXTAREA_PROPERTY_COLOR)!= null? confProps.getProperty(TEXTAREA_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				textfield = Color.decode(confProps.getProperty(TEXTFIELD_PROPERTY_COLOR)!= null? confProps.getProperty(TEXTFIELD_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				textpane = Color.decode(confProps.getProperty(TEXTPANE_PROPERTY_COLOR)!= null? confProps.getProperty(TEXTPANE_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				tooltip = Color.decode(confProps.getProperty(TOOLTIP_PROPERTY_COLOR)!= null? confProps.getProperty(TOOLTIP_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				viewport = Color.decode(confProps.getProperty(VIEWPORT_PROPERTY_COLOR)!= null? confProps.getProperty(VIEWPORT_PROPERTY_COLOR).trim():DEFAULT_COLOR); 
				currency = confProps.getProperty(CURRENCY)!= null? confProps.getProperty(CURRENCY).toUpperCase().trim():DEFAULT_CURRENCY; 
				
			} finally
			{
				if (fis != null)
				{
					fis.close();
				}
			}
		}
		catch(Exception e) {
			Log.warning("Error obtaining properties from zelcash_ui.properties file due to: {0} {1}",
					e.getClass().getName(), e.getMessage());
		}	
	}
	
	private static void Copy(InputStream source , String destination) {

		Log.info("Copying ->" + source + "\n\tto ->" + destination);

        try {
            Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
        	Log.warning("Error to copy zelcash_ui.properties from resources due to: {0} {1}",
					ex.getClass().getName(), ex.getMessage());
        }


    }

}