/************************************************************************************************
 *   ____________ _   _  _____          _      _____ _    _ _______          __   _ _      _   
 *  |___  /  ____| \ | |/ ____|        | |    / ____| |  | |_   _\ \        / /  | | |    | |  
 *     / /| |__  |  \| | |     __ _ ___| |__ | |  __| |  | | | |  \ \  /\  / /_ _| | | ___| |_ 
 *    / / |  __| | . ` | |    / _` / __| '_ \| | |_ | |  | | | |   \ \/  \/ / _` | | |/ _ \ __|
 *   / /__| |____| |\  | |___| (_| \__ \ | | | |__| | |__| |_| |_   \  /\  / (_| | | |  __/ |_ 
 *  /_____|______|_| \_|\_____\__,_|___/_| |_|\_____|\____/|_____|   \/  \/ \__,_|_|_|\___|\__|
 *                                                                                             
 * Copyright (c) 2016-2018 The ZEN Developers
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 **********************************************************************************/
package com.vaklinov.zcashui.msg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;

import com.cabecinha84.zelcashui.ZelCashJButton;
import com.cabecinha84.zelcashui.ZelCashJCheckBox;
import com.cabecinha84.zelcashui.ZelCashJDialog;
import com.cabecinha84.zelcashui.ZelCashJFrame;
import com.cabecinha84.zelcashui.ZelCashJLabel;
import com.cabecinha84.zelcashui.ZelCashJPanel;
import com.cabecinha84.zelcashui.ZelCashJTextField;
import com.vaklinov.zcashui.LanguageUtil;
import com.vaklinov.zcashui.Log;
import com.vaklinov.zcashui.StatusUpdateErrorReporter;
import com.vaklinov.zcashui.Util;


/**
 * Dialog showing the messaging options and allowing them to be edited.
 */
public class MessagingOptionsEditDialog
	extends ZelCashJDialog
{
	protected ZelCashJFrame parentFrame;
	protected MessagingStorage storage;
	protected StatusUpdateErrorReporter errorReporter;
	
	protected ZelCashJLabel infoLabel;
	protected ZelCashJPanel buttonPanel;
	
	protected ZelCashJTextField amountTextField;
	protected ZelCashJTextField transactionFeeTextField;
	protected ZelCashJCheckBox  automaticallyAddUsers;
	private static LanguageUtil langUtil = LanguageUtil.instance();
	
	public MessagingOptionsEditDialog(ZelCashJFrame parentFrame, MessagingStorage storage, StatusUpdateErrorReporter errorReporter)
		throws IOException
	{
		this.parentFrame   = parentFrame;
		this.storage       = storage;
		this.errorReporter = errorReporter;
		
		this.setTitle(langUtil.getString("messaging.options.title"));
		this.setModal(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		MessagingOptions options = this.storage.getMessagingOptions();
			
		this.getContentPane().setLayout(new BorderLayout(0, 0));
			
		ZelCashJPanel tempPanel = new ZelCashJPanel(new BorderLayout(0, 0));
		tempPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		infoLabel = new ZelCashJLabel(
				langUtil.getString("messaging.options.info"));
	    tempPanel.add(infoLabel, BorderLayout.CENTER);
		this.getContentPane().add(tempPanel, BorderLayout.NORTH);
			
		ZelCashJPanel detailsPanel = new ZelCashJPanel();
		detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
		
		addFormField(detailsPanel, langUtil.getString("messaging.options.auto.users"),   
				     automaticallyAddUsers = new ZelCashJCheckBox());
		addFormField(detailsPanel, langUtil.getString("messaging.options.auto.amount"),   amountTextField = new ZelCashJTextField(12));
		addFormField(detailsPanel, langUtil.getString("messaging.options.txn.fee"),  transactionFeeTextField = new ZelCashJTextField(12));
		
		DecimalFormatSymbols decSymbols = new DecimalFormatSymbols(Locale.ROOT);
		automaticallyAddUsers.setSelected(options.isAutomaticallyAddUsersIfNotExplicitlyImported());
		amountTextField.setText(new DecimalFormat("########0.00######", decSymbols).format(options.getAmountToSend()));
		transactionFeeTextField.setText(new DecimalFormat("########0.00######", decSymbols).format(options.getTransactionFee()));
		
		detailsPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		this.getContentPane().add(detailsPanel, BorderLayout.CENTER);

		// Lower buttons - by default only close is available
		buttonPanel = new ZelCashJPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 3));
		ZelCashJButton closeButon = new ZelCashJButton("Close");
		buttonPanel.add(closeButon);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		closeButon.addActionListener(new ActionListener()
		{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					MessagingOptionsEditDialog.this.setVisible(false);
					MessagingOptionsEditDialog.this.dispose();
				}
		});
		
		ZelCashJButton saveButon = new ZelCashJButton(langUtil.getString("messaging.options.save"));
		buttonPanel.add(saveButon);
		saveButon.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					String amountToSend = MessagingOptionsEditDialog.this.amountTextField.getText();
					String transactionFee = MessagingOptionsEditDialog.this.transactionFeeTextField.getText();
					
					if ((!MessagingOptionsEditDialog.this.verifyNumericField(langUtil.getString("messaging.options.amount"), amountToSend)) ||
						(!MessagingOptionsEditDialog.this.verifyNumericField(langUtil.getString("messaging.options.txn"), transactionFee)))
					{
						return;
					}
					
					MessagingOptions options = MessagingOptionsEditDialog.this.storage.getMessagingOptions();
					
					options.setAmountToSend(Double.parseDouble(amountToSend));				
					options.setTransactionFee(Double.parseDouble(transactionFee));
					options.setAutomaticallyAddUsersIfNotExplicitlyImported(
						MessagingOptionsEditDialog.this.automaticallyAddUsers.isSelected());
					
					MessagingOptionsEditDialog.this.storage.updateMessagingOptions(options);
					
					MessagingOptionsEditDialog.this.setVisible(false);
					MessagingOptionsEditDialog.this.dispose();
				} catch (Exception ex)
				{
					Log.error("Unexpected error in editing own messaging identity!", ex);
					MessagingOptionsEditDialog.this.errorReporter.reportError(ex, false);
				}
			}
		});

		this.pack();
		this.setLocation(100, 100);
		this.setLocationRelativeTo(parentFrame);
	}

	
	private boolean verifyNumericField(String name, String value)
	{
		if (Util.stringIsEmpty(value))
		{
	        JOptionPane.showMessageDialog(
        		this.parentFrame,
        		langUtil.getString("messaging.options.error", name),
                langUtil.getString("messaging.options.error.mandatory"), JOptionPane.ERROR_MESSAGE);
	        return false;
		}
		
		try
		{
			double dVal = Double.parseDouble(value);
			
			if (dVal < 0)
			{
		        JOptionPane.showMessageDialog(
		        	this.parentFrame,
		        	langUtil.getString("messaging.options.error.negative", name),
		        	langUtil.getString("messaging.options.error.field.negative"), JOptionPane.ERROR_MESSAGE);
		        return false;			
			}
		} catch (NumberFormatException nfe)
		{
	        JOptionPane.showMessageDialog(
	        	this.parentFrame,
	        	langUtil.getString("messaging.options.error.numeric", name),
	            langUtil.getString("messaging.options.error.not.numeric"), JOptionPane.ERROR_MESSAGE);
		    return false;			
		}
		
		return true;
	}
	
	
	private void addFormField(ZelCashJPanel detailsPanel, String name, JComponent field)
	{
		ZelCashJPanel tempPanel = new ZelCashJPanel(new FlowLayout(FlowLayout.LEFT, 4, 2));
		ZelCashJLabel tempLabel = new ZelCashJLabel(name, JLabel.RIGHT);
		// TODO: hard sizing of labels may not scale!
		final int width = new ZelCashJLabel(langUtil.getString("messaging.options.auto.amount")).getPreferredSize().width + 30;
		tempLabel.setPreferredSize(new Dimension(width, tempLabel.getPreferredSize().height));
		tempPanel.add(tempLabel);
		tempPanel.add(field);
		detailsPanel.add(tempPanel);
	}
	
} 
