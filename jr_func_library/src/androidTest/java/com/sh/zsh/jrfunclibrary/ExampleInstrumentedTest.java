�}�* 
 |   ���]9K    ��+�9��fܤ����~4�E��G���;=ڍ4�l���|���"E���+DJJ��4}ޙ*YN��g:[�p����Au�v{��V'����u�f�T1�RT��z4���8n0���A�x�s�U;RҞ,�?"|\�[�Ѧ���9�Q�~��xW�5?
nT� y��1#��!��9�/$�t�O�����\+�����]�7MnU�~[&��gjC�&���񶭽�_�z7~��Ԑ1("|cX�2�#��<Y��72�4����[E���{	��r��,0y`��0Zx�8��@@�F¦����j>t�T�x��鉦�}mq�mB۠p��΀/$V4]#a*3C;��)$x��l�y��֜���2N���+M����]�oGo��HvD�$d���b��9.�y��ϰ6���m�9`�F V�2��{uG~F����;��ڢ�.�ޢ32��\�L�#���-���#ׁ��j�)7T���b�$�g�;est
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.sh.zsh.jr_func_library.test", appContext.getPackageName());
    }
}
