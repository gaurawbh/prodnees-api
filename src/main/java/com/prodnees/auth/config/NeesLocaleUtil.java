/*
 * Copyright (c) This is the property fo Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing to will be prosecuted by Gauri Baba.
 */

package com.prodnees.auth.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class NeesLocaleUtil {
    private static final List<String> invalidCurrencyCodes = Arrays.asList("XTS", "XXX");

    private NeesLocaleUtil() {

    }

    public static List<String> applicationSupportedCountryList() {
        return Arrays.asList(new Locale("", "EG").getDisplayCountry(),
                new Locale("", "NP").getDisplayCountry(),
                new Locale("", "IN").getDisplayCountry());
    }

    public static List<String> allISOCountryList() {
        List<String> countryList = new ArrayList<>();
        String[] isoCountries = Locale.getISOCountries();
        Arrays.stream(isoCountries).forEach(isoCountry -> countryList.add(new Locale("", isoCountry).getDisplayName()));
        return countryList;
    }

    public static List<String> allISOCountryListLowerCase() {
        List<String> countryList = new ArrayList<>();
        String[] isoCountries = Locale.getISOCountries();
        Arrays.stream(isoCountries).forEach(isoCountry -> countryList.add(new Locale("", isoCountry).getDisplayName().toLowerCase()));
        return countryList;
    }

    public static boolean isValidCountryName(String name) {
        return allISOCountryListLowerCase().contains(name.toLowerCase());
    }

    public static Map<String, String> allZoneIdsAndItsOffSet() {
        Map<String, String> result = new HashMap<>();

        LocalDateTime localDateTime = LocalDateTime.now();

        for (String zoneId : ZoneId.getAvailableZoneIds()) {

            ZoneId id = ZoneId.of(zoneId);

            // LocalDateTime -> ZonedDateTime
            ZonedDateTime zonedDateTime = localDateTime.atZone(id);

            // ZonedDateTime -> ZoneOffset
            ZoneOffset zoneOffset = zonedDateTime.getOffset();

            //replace Z to +00:00
            String offset = zoneOffset.getId().replaceAll("Z", "+00:00");

            result.put(id.toString(), offset);

        }

        return result;
    }

    public static Map<String, String> applicationZoneIdsAndItsOffSet() {
        Map<String, String> result = new HashMap<>();
        List<String> applicationZones = new ArrayList<>();
        applicationZones.add("Asia/Kathmandu");
        applicationZones.add("Africa/Cairo");
        applicationZones.add("Europe/London");
        applicationZones.add("Asia/Kolkata");
        LocalDateTime localDateTime = LocalDateTime.now();

        for (String zone : applicationZones) {
            String offSet = localDateTime.atZone(ZoneId.of(zone)).getOffset().getId().replaceAll("Z", "+00:00");
            result.put(zone, offSet);
        }

        return result;
    }

    public static Map<String, String> getAllCurrencyCodesAndNames() {

        Set<Currency> currencySet = Currency.getAvailableCurrencies();
        Map<String, String> currencyCodeAndNameMap = new HashMap<>();
        currencySet.forEach(curr ->{
            if(!invalidCurrencyCodes.contains(curr.getCurrencyCode())){
                currencyCodeAndNameMap.put(curr.getCurrencyCode(), curr.getDisplayName());
            }
        });
        return currencyCodeAndNameMap;
    }

    public static List<String> getAllCurrencyCodes() {

        return Currency.getAvailableCurrencies().stream()
                .map(Currency::getCurrencyCode)
                .filter(curr -> !invalidCurrencyCodes.contains(curr))
                .collect(Collectors.toList());
    }

    public static boolean isValidCurrencyCode(String currencyCode) {
        return getAllCurrencyCodes().contains(currencyCode);
    }
}
