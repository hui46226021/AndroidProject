�}4* 
   �pY����9&	��� +�)w�Bܤ��������"�YQ@����"�q�C�z�b�n���}����L�tRv��)}۶�)7q� '��6�O��EqR�	�8���j��� �_�^l�/����t=		Cu�o��l[0�Ǌ[���)&���캞`y�' ��rť�j�e�$o+��xK�@L~���h���c�y8+�������S]�5N轑� ��Ia&�����O��#���VV����r[�w��T�Y�#���(�Or%8��t��s�~��Q�����?R�N�B)\�@�F¦����j>t�T�x��鉦�}mq�mB۠p��΀/$V4]#a*3C;��)$x��l�y��֜���2N���+M����]�oGo��HvD�$d���b��9.�y��ϰ6���m�9`�F V�2��{uG~F����;��ڢ�.�ޢ32��\�L�#���-���#ׁ��j�)7T���b�$�g�;   return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
