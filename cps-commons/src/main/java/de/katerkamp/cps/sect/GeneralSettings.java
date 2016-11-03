/*
 * Copyright (c) 2016 Stefan Katerkamp. All rights reserved.
 */
package de.katerkamp.cps.sect;

import de.katerkamp.cps.ds.TalkPermitTone;
import javax.validation.constraints.NotNull;


/**
 * Interface for section GeneralSettings.
 * Including data structure limits for use with Java Bean Validator.
 *
 * @todo add all constraints
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public interface GeneralSettings {

	public String getInfoScreenLine1();

	public void setInfoScreenLine1(String infoScreenLine1);

	public String getInfoScreenLine2();

	public void setInfoScreenLine2(String infoScreenLine2);

	public boolean isMonitorTypeOpen();

	public void setMonitorTypeOpen(boolean monitorTypeOpen);

	public boolean isDisableAllLeds();

	public void setDisableAllLeds(boolean disableAllLeds);

	public TalkPermitTone getTalkPermitTone();

	public void setTalkPermitTone(TalkPermitTone talkPermitTone);

	public boolean isPasswordAndLockEnabled();

	public void setPasswordAndLockEnabled(boolean passwordAndLockEnabled);

	public boolean isChFreeIndicationToneOn();

	public void setChFreeIndicationToneOn(boolean chFreeIndicationToneOn);

	public boolean isDisableAllTone();

	public void setDisableAllTone(boolean disableAllTone);

	public boolean isSaveModeReceive();

	public void setSaveModeReceive(boolean saveModeReceive);

	public boolean isSavePreamble();

	public void setSavePreamble(boolean savePreamble);

	public boolean isPictureIntroScreen();

	public void setPictureIntroScreen(boolean pictureIntroScreen);

	@NotNull	
	public int getRadioId();

	public void setRadioId(int radioId);

	public int getTxPreamble();

	public void setTxPreamble(byte txPreamble);

	public int getGroupCallHangTime();

	public void setGroupCallHangTime(byte groupCallHangTime);

	public int getPrivateCallHangTime();

	public void setPrivateCallHangTime(byte privateCallHangTime);

	public int getVoxSensitivity();

	public void setVoxSensitivity(byte voxSensitivity);

	public int getRxLowBatteryInterval();

	public void setRxLowBatteryInterval(byte rxLowBatteryInterval);

	public int getCallAlertTone();

	public void setCallAlertTone(byte callAlertTone);

	public int getLoneWorkerRespTime();

	public void setLoneWorkerRespTime(byte loneWorkerRespTime);

	public int getLoneWorkerReminderTime();

	public void setLoneWorkerReminderTime(byte loneWorkerReminderTime);

	public int getScanDigitalHangTime();

	public void setScanDigitalHangTime(byte scanDigitalHangTime);

	public byte getScanAnalogHangTime();

	public void setScanAnalogHangTime(byte scanAnalogHangTime);

	public byte getKeypadLockTime();

	public void setKeypadLockTime(byte keypadLockTime);

	public byte getMode();

	public void setMode(byte mode);

	public int getPowerOnPIN();

	public void setPowerOnPIN(int powerOnPIN);

	public int getRadioProgPIN();

	public void setRadioProgPIN(int radioProgPIN);

	public String getPcProgPassword();

	public void setPcProgPassword(String pcProgPassword);

	public String getRadioName();

	public void setRadioName(String radioName);

}
