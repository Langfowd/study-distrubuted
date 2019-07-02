package provider;

import api.service.CalcService;

public class CalcServiceImpl implements CalcService {

    @Override
    public int add(int a, int b) {
        return a+b;
    }

    @Override
    public int mutli(int a, int b) {
        return a*b;
    }


    @Override
    public int div(int a, int b) {

        return a/b;
    }

    @Override
    public int sub(int a, int b) {
        return a-b;
    }
}
