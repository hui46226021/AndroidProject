�}�* 
 "  �%"��?�    ��� +�1i�b�����ǐ��JӆYX�Dn˞� ��6P�_�E�e�DX~�RX4=n������/D䝂->ٜr9dV�d�9v/��L��^�����$0�TG=������W�Q]uT1F�1�K%�{�YL�e��VH.��i���뱠m��h���H�e�M�~�b!���l"��X��1�e"�?�s�5����VZ�;�	Nj����8*߆@�;��>�e�|�7���ҨBǆ6ΪRLY�#���(�Or%8��t��s�~��Q�����?R�N�B)\�@�F¦����j>t�T�x��鉦�}mq�mB۠p��΀/$V4]#a*3C;��)$x��l�y��֜���2N���+M����]�oGo��HvD�$d���b��9.�y��ϰ6���m�9`�F V�2��{uG~F����;��ڢ�.�ޢ32��\�L�#���-���#ׁ��j�)7T���b�$�g�;nchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.add(url, cookies);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        return cookieStore.get(url);
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }
}
