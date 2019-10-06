package com.example.effortmanagement.networking;

import com.example.effortmanagement.adapter.RetrofitAdapter;
import com.example.effortmanagement.networking.API.AccountService;
import com.example.effortmanagement.networking.API.RoleService;

public class NetworkingUtils {
    private static AccountService accountService;
    private static RoleService roleService;

    public static AccountService getUserApiInstance() {
        if (accountService == null)
            accountService = RetrofitAdapter.getInstance().create(AccountService.class);

        return accountService;
    }

    public static RoleService getRoleApiInstance() {
        if (roleService == null)
            roleService = RetrofitAdapter.getInstance().create(RoleService.class);

        return roleService;
    }


}