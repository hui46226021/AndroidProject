�}�* 
 �  )捝2���    ���+�ѷ�b��w����T�a��<E4	n�K���W��oI2ΦEGqn��)�(���|V���oF	�$6λ���E���Z$z��b3/��1cL#®�"����m��x����U������[\P��ž�wJ%������ T�'�Uy���vA���+��Y����Q���=����-�������A�?f�\^9Vv�z�G��#Sr��F~2W����CI�/�G�n���AT,'u��s�O����[uX���%�R��Y���`G��TaeZ�A�cs�{E^Y���#����H}�5e�/�G��ɛ��T��"q��y�q�:���4�`S��G4�����v��H�ŢTG���{�&g���;�3@����ݒU�n�/��ϰ6���m�9`�F V�2��{uG~F����;��ڢ�.�ޢ32��\�L�#���-���#ׁ��j�)7T���b�$�g�;lD&�{��Ӛ�2�c������9~�l���!xG����y{Z�Gf�\��їh)�� ��OT�Ȕ�lߥ���.!k�_���k>����S�E���B5T������SA@ee���9vo�}��\Cv�*�>�U �g�[��o�J.EMN�+���8|�@WRO�<r�0�{�0��)-�o�>�\ß�A*HՌ�����@C�~��J�/�VN:NO��&U�T|q'�UcA�째r��y�]��Y�&l����8r녌8 ����}�|rP����y��ͭ������Y���ؗ��cH��o`sز�D0�B`<�:��I�~6̍����6^%�J�t�s�Ϛ�o�&�͝Х��z���x[����c�{yi~-����w!�5����%t'mC�)̙�)T�9�V�ȉ�b��-?��5ԄO�r�����Go�S�ha�_����[�dơ{��򍶤j���7ۘ���-�������]D��7�)�#�Je ���9
    /**
     * 获取全局临时缓存
     * @param key
     * @return
     */
    public Object getObjce(String key){
      return   get(key);
    }

    public void setLoginPage(Class classz){
        putObject("loginPage",classz);
    }
    public Class getLoginPage(){
        if(getObjce("loginPage")!=null){
            return (Class) getObjce("loginPage");
        }
        return null;
    }
}
