package com.example.grifmood;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DialogFragmentMy#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogFragmentMy extends androidx.fragment.app.DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DialogFragmentMy() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogFragmentMy newInstance(String param1, String param2) {
        DialogFragmentMy fragment = new DialogFragmentMy();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder.setTitle("Команды для управления голосом:").setMessage("1. Открой заметки;\n2. Открой статистику;\n3. Открой календарь;\n4. Открой тесты;\n5. Добавь заметку;\n6. Добавь заметку настроение (ваше настроение),(вид деятельности),(количество часов),(количество минут);\n7.Открой тест на диагностику невроза;\n8. Открой тест на диагностику тревоги;\n9. Открой тест на оценку эмоционального интеллекта;\n10. Открой тест на оценку созависимости;\n11. Открой тест на диагностику депрессии;\n12. Открой тест на оценку уровня общительности.").create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }
}