�}�* 
 �  ���Ps�9&	��+�1�'gܤ��}jҔ8G`���5�@� �y�-{d�NF�KJ N ���` ����|.>P2� ����۸Ա��ui��YDX�Z�Z,Έ�4p��Gk�²ߚ �$#�>UQ�Gs���/	z�?e�k�*��5|�.A۹��Pz����4h�{������ԭ��r\���#�,P�bt����l!�c�HV\���lc�E��P�I�b���Kv&Aؙ��Ol<��p�B���AO��#��V���t��s�~��Q�����?R�N�B)\�@�F¦����j>t�T�x��鉦�}mq�mB۠p��΀/$V4]#a*3C;��)$x��l�y��֜���2N���+M����]�oGo��HvD�$d���b��9.�y��ϰ6���m�9`�F V�2��{uG~F����;��ڢ�.�ޢ32��\�L�#���-���#ׁ��j�)7T���b�$�g�;l(ʹ���h�6r�������ȭ�G�t�cx#�0-<`Ŷw��V�ߐO!��cI�6a*_~1�>K�B��#�<��Dj^�y*:�n�e4�{��6��ı��2z��^����̽]'��b\;�*ŧ����Х���Qe5�3h_�!�+���;^�s�4��8ù�+�������C�Y5̀�1sN��0J|T�z���)�cH�qV����X0~7K�w�d2��޳*"��ԏ(1cH,!Su��yo����|Бc�ٷ�ty��m����p�:Rox�:_<t� e���M�f�V4��|f&�r�6��pe�=�0�Ȟ���Us�3�a�Ǡ�O��Q�
��=I��aB%N��s�5S;��xa3�1UyD��p�ƾu�?�y���ύ�P
�E�ܶ�$��Ui��>��o A��$��(�3�g���Zj��w�g��h'1��G�kn���E��� V���;�p7�����@��Z��0�#�¡�R����M�p!�Jg�Y�l��s.mContext = context;
        this.listener = listener;
        this.scaleType = scaleType;
    }

    @Override
    protected Fragment getItemFragment(Images images, int position) {
        return ViewPagerItemFragment.instantiateWithArgs(mContext, images, listener,scaleType);
    }
}
