package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhoneInfo {

    private static SimpleDateFormat gDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public long phoneNumber;
    private int activationDate;
    private int deactivationDate;

    public PhoneInfo(long _phoneNumber, int _activationDate, int _deactivationDate) {
        phoneNumber = _phoneNumber;
        activationDate = _activationDate;
        deactivationDate = _deactivationDate;
    }


    /**
     * Parses the given string to produce a PhoneInfo
     * @param _serializedPhoneInfo Phone info serialized string. E.g: 0987000001,2016-03-01,2016-05-01
     * @return Returns PhoneInfo, otherwise returns null
     */
    public static PhoneInfo parsePhoneInfo(String _serializedPhoneInfo) {
        PhoneInfo result = null;

        if (_serializedPhoneInfo != null && !_serializedPhoneInfo.isEmpty()) {
            String[] phoneInfoParts = _serializedPhoneInfo.split(",");

            if (phoneInfoParts.length > 1) {
                try {
                    long phoneNumber = 0;//Long.parseLong(phoneInfoParts[0]);

                    // No need to store millisecond, so div by 1000 then cast to int
                    int activationDate = (int)(gDateFormat.parse(phoneInfoParts[1]).getTime() / 1000);
                    int deactivationDate = 0;
                    if (phoneInfoParts.length > 2) {
                        //deactivationDate = (int)(gDateFormat.parse(phoneInfoParts[2]).getTime() / 1000);
                    }

                    result = new PhoneInfo(phoneNumber, activationDate, deactivationDate);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }

        return result;
    }


    @Override
    public String toString() {
        return phoneNumber + " - " + new Date(activationDate * 1000L) + " - " + new Date(deactivationDate * 1000L);
    }
}