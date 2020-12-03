package com.example.cosmicomapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends Fragment {

    private String usertype;
    public com.google.android.material.textfield.TextInputLayout website, gender, phone, DOB, name, email, password, cPassword, country;
    com.google.android.material.textfield.TextInputEditText dob1;
    public Button mBtn;
    final Calendar myCalendar = Calendar.getInstance();
    RegisterRequest registerRequest;
    private static final String[] COUNTRIES = new String[]{
            "AF Afghanistan +93", "AX Aland Islands +358", "AL Albania +355", "DZ Algeria +213", "AS AmericanSamoa +1684", "AD Andorra +376", "AO Angola +244", "AI Anguilla +1264", "AQ Antarctica +672", "AG Antigua and Barbuda +1268", "AR Argentina +54", "AM Armenia +374", "AW Aruba +297", "AC Ascension Island +247", "AU Australia +61", "AT Austria +43", "AZ Azerbaijan +994", "BS Bahamas +1242", "BH Bahrain +973", "BD Bangladesh +880", "BB Barbados +1246", "BY Belarus +375", "BE Belgium +32", "BZ Belize +501", "BJ Benin +229", "BM Bermuda +1441", "BT Bhutan +975", "BO Bolivia +591", "BA Bosnia and Herzegovina +387", "BW Botswana +267", "BR Brazil +55", "IO British Indian Ocean Territory +246", "BN Brunei Darussalam +673", "BG Bulgaria +359", "BF Burkina Faso +226", "BI Burundi +257", "KH Cambodia +855", "CM Cameroon +237", "CA Canada +1", "CV Cape Verde +238", "KY Cayman Islands +1345", "CF Central African Republic +236", "TD Chad +235", "CL Chile +56", "CN China +86", "CX Christmas Island +61", "CC Cocos (Keeling) Islands +61", "CO Colombia +57", "KM Comoros +269", "CG Congo +242", "CK Cook Islands +682", "CR Costa Rica +506", "HR Croatia +385", "CU Cuba +53", "CY Cyprus +357", "CZ Czech Republic +420", "CD Democratic Republic of the Congo +243", "DK Denmark +45", "DJ Djibouti +253", "DM Dominica +1767", "DO Dominican Republic +1849", "EC Ecuador +593", "EG Egypt +20", "SV El Salvador +503", "GQ Equatorial Guinea +240", "ER Eritrea +291", "EE Estonia +372", "SZ Eswatini +268", "ET Ethiopia +251", "FK Falkland Islands (Malvinas) +500", "FO Faroe Islands +298", "FJ Fiji +679", "FI Finland +358", "FR France +33", "GF French Guiana +594", "PF French Polynesia +689", "GA Gabon +241", "GM Gambia +220", "GE Georgia +995", "DE Germany +49", "GH Ghana +233", "GI Gibraltar +350", "GR Greece +30", "GL Greenland +299", "GD Grenada +1473", "GP Guadeloupe +590", "GU Guam +1671", "GT Guatemala +502", "GG Guernsey +44", "GN Guinea +224", "GW Guinea-Bissau +245", "GY Guyana +592", "HT Haiti +509", "VA Holy See (Vatican City State) +379", "HN Honduras +504", "HK Hong Kong +852", "HU Hungary +36", "IN India +91", "IS Iceland +354", "ID Indonesia +62", "IR Iran +98", "IQ Iraq +964", "IE Ireland +353", "IM Isle of Man +44", "IL Israel +972", "IT Italy +39", "CI Ivory Coast / Cote d'Ivoire +225", "JM Jamaica +1876", "JP Japan +81", "JE Jersey +44", "JO Jordan +962", "KZ Kazakhstan +77", "KE Kenya +254", "KI Kiribati +686", "KP Korea, Democratic People's Republic of Korea +850", "KR Korea, Republic of South Korea +82", "XK Kosovo +383", "KW Kuwait +965", "KG Kyrgyzstan +996", "LA Laos +856", "LV Latvia +371", "LB Lebanon +961", "LS Lesotho +266", "LR Liberia +231", "LY Libya +218", "LI Liechtenstein +423", "LT Lithuania +370", "LU Luxembourg +352", "MO Macau +853", "MG Madagascar +261", "MW Malawi +265", "MY Malaysia +60", "MV Maldives +960", "ML Mali +223", "MT Malta +356", "MH Marshall Islands +692", "MQ Martinique +596", "MR Mauritania +222", "MU Mauritius +230", "YT Mayotte +262", "MX Mexico +52", "FM Micronesia, Federated States of Micronesia +691", "MD Moldova +373", "MC Monaco +377", "MN Mongolia +976", "ME Montenegro +382", "MS Montserrat +1664", "MA Morocco +212", "MZ Mozambique +258", "MM Myanmar +95", "NA Namibia +264", "NR Nauru +674", "NP Nepal +977", "NL Netherlands +31", "AN Netherlands Antilles +599", "NC New Caledonia +687", "NZ New Zealand +64", "NI Nicaragua +505", "NE Niger +227", "NG Nigeria +234", "NU Niue +683", "NF Norfolk Island +672", "MK North Macedonia +389", "MP Northern Mariana Islands +1670", "NO Norway +47", "OM Oman +968", "PK Pakistan +92", "PW Palau +680", "PS Palestine +970", "PA Panama +507", "PG Papua New Guinea +675", "PY Paraguay +595", "PE Peru +51", "PH Philippines +63", "PN Pitcairn +872", "PL Poland +48", "PT Portugal +351", "PR Puerto Rico +1939", "QA Qatar +974", "RE Reunion +262", "RO Romania +40", "RU Russia +7", "RW Rwanda +250", "BL Saint Barthelemy +590", "SH Saint Helena, Ascension and Tristan Da Cunha +290", "KN Saint Kitts and Nevis +1869", "LC Saint Lucia +1758", "MF Saint Martin +590", "PM Saint Pierre and Miquelon +508", "VC Saint Vincent and the Grenadines +1784", "WS Samoa +685", "SM San Marino +378", "ST Sao Tome and Principe +239", "SA Saudi Arabia +966", "SN Senegal +221", "RS Serbia +381", "SC Seychelles +248", "SL Sierra Leone +232", "SG Singapore +65", "SX Sint Maarten +1721", "SK Slovakia +421", "SI Slovenia +386", "SB Solomon Islands +677", "SO Somalia +252", "ZA South Africa +27", "GS South Georgia and the South Sandwich Islands +500", "SS South Sudan +211", "ES Spain +34", "LK Sri Lanka +94", "SD Sudan +249", "SR Suriname +597", "SJ Svalbard and Jan Mayen +47", "SE Sweden +46", "CH Switzerland +41", "SY Syrian Arab Republic +963", "TW Taiwan +886", "TJ Tajikistan +992", "TZ Tanzania, United Republic of Tanzania +255", "TH Thailand +66", "TL Timor-Leste +670", "TG Togo +228", "TK Tokelau +690", "TO Tonga +676", "TT Trinidad and Tobago +1868", "TN Tunisia +216", "TR Turkey +90", "TM Turkmenistan +993", "TC Turks and Caicos Islands +1649", "TV Tuvalu +688", "UG Uganda +256", "UA Ukraine +380", "AE United Arab Emirates +971", "GB United Kingdom +44", "US United States +1", "UY Uruguay +598", "UZ Uzbekistan +998", "VU Vanuatu +678", "VE Venezuela, Bolivarian Republic of Venezuela +58", "VN Vietnam +84", "VG Virgin Islands, British +1284", "VI Virgin Islands, U.S. +1340", "WF Wallis and Futuna +681", "YE Yemen +967", "ZM Zambia +260", "ZW Zimbabwe +263"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_fragment, container, false);

        AutoCompleteTextView textView = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
        gender = rootView.findViewById(R.id.gender);
        DOB = rootView.findViewById(R.id.dob);
        dob1 = rootView.findViewById(R.id.dob1);
        name = rootView.findViewById(R.id.full_name);
        email = rootView.findViewById(R.id.email);
        country = rootView.findViewById(R.id.countryCode);
        phone = rootView.findViewById(R.id.phone);
        password = rootView.findViewById(R.id.password);
        cPassword = rootView.findViewById(R.id.confirm_password);
        website = rootView.findViewById(R.id.website);
        mBtn = rootView.findViewById(R.id.register_button);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.select_dialog_item, COUNTRIES);
        textView.setAdapter(adapter);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.register_user_type);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        registerRequest = retrofit.create(RegisterRequest.class);

        dob1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                usertype = adapterView.getSelectedItem().toString();
                if (usertype.equals("Merchant") || usertype.equals("Shipper")) {
                    gender.setVisibility(View.GONE);
                    phone.setVisibility(View.GONE);
                    DOB.setVisibility(View.GONE);
                    website.setVisibility(View.VISIBLE);
                } else {
                    gender.setVisibility(View.VISIBLE);
                    phone.setVisibility(View.VISIBLE);
                    DOB.setVisibility(View.VISIBLE);
                    website.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                usertype = "User";
            }
        });

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String webs = website.getEditText().getText().toString();
                String Name = name.getEditText().getText().toString();
                String Email = email.getEditText().getText().toString();
                String gnd = gender.getEditText().getText().toString();
                String Phone = phone.getEditText().getText().toString();
                String Dob = DOB.getEditText().getText().toString();
                String cc = country.getEditText().getText().toString();
                String cc1 = cc.split(" ")[0];
                Log.d("abc", cc1);
                String Password = password.getEditText().getText().toString();
                String confirmP = cPassword.getEditText().getText().toString();
                Map<String, String> fields = new HashMap<>();
                if (usertype.equals("User")) {

                    if (TextUtils.isEmpty(Name)) {
                        name.setError("Password is required");
                        return;
                    }
                    if (TextUtils.isEmpty(Email)) {
                        email.setError("Password is required");
                        return;
                    }
                    if (TextUtils.isEmpty(gnd)) {
                        gender.setError("Password is required");
                        return;
                    }
                    if (TextUtils.isEmpty(Phone)) {
                        phone.setError("Password is required");
                        return;
                    }
                    if (TextUtils.isEmpty(Dob)) {
                        DOB.setError("Password is required");
                        return;
                    }
                    if (TextUtils.isEmpty(cc)) {
                        country.setError("Password is required");
                        return;
                    }
                    fields.put("name", Name);
                    fields.put("email", Email);
                    fields.put("password", Password);
                    fields.put("gender", gnd);
                    fields.put("phone", Phone);
                    fields.put("dob", Dob);
                    fields.put("country_code", cc1);
                    fields.put("usertype", "user");
                    Call<SimpleAdapter> call = registerRequest.createPost(fields);
                    call.enqueue(new Callback<SimpleAdapter>() {
                        @Override
                        public void onResponse(Call<SimpleAdapter> call, Response<SimpleAdapter> response) {
                            Log.d("abc", String.valueOf(response.code()));
                            if(response.code()==200){
                                Toast.makeText(getContext(),"Success",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getContext(),"Failure",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SimpleAdapter> call, Throwable t) {
                            Toast.makeText(getContext(),"Failure",Toast.LENGTH_LONG).show();

                        }
                    });

                } else {

                    Log.d("abc",usertype.substring(0,1).toLowerCase().concat(usertype.substring(1)));
                    if (TextUtils.isEmpty(webs)) {
                        website.setError("Email is required");
                        return;
                    }
                    if (TextUtils.isEmpty(Name)) {
                        name.setError("Password is required");
                        return;
                    }
                    if (TextUtils.isEmpty(Email)) {
                        email.setError("Password is required");
                        return;
                    }
                    if (TextUtils.isEmpty(Password)) {
                        password.setError("Password is required");
                        return;
                    }
                    if (TextUtils.isEmpty(confirmP)) {
                        cPassword.setError("Password is required");
                        return;
                    }
                    if (TextUtils.isEmpty(cc)) {
                        country.setError("Password is required");
                        return;
                    }

                    fields.put("name", Name);
                    fields.put("email", Email);
                    fields.put("password", Password);
                    fields.put("website", webs);
                    fields.put("country_code", cc1);
                    fields.put("usertype", usertype.substring(0,0).toLowerCase().concat(usertype.substring(1,usertype.length()-1)));

                    Call<SimpleAdapter> call = registerRequest.createPost(fields);
                    call.enqueue(new Callback<SimpleAdapter>() {
                        @Override
                        public void onResponse(Call<SimpleAdapter> call, Response<SimpleAdapter> response) {
                            Log.d("abc", String.valueOf(response.code()));
                            if(response.code()==200){
                                Toast.makeText(getContext(),"Success",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getContext(),"Failure",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SimpleAdapter> call, Throwable t) {
                            Toast.makeText(getContext(),"Failure",Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.register_user_type, android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinAdapter);

        return rootView;
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob1.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}