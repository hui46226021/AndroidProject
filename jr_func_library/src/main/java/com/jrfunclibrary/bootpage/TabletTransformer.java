�}* 
 �  E�n�(1    ��1+���;Cݤ���:��m�>3�tv�e���<I����P��Kc��n�Ļf�t�'���R�ыr���g�躢���3���`�(�l�1S�[v��3��f�x�>~�l�
�I�^���t�,X�����I�a�یW�|`B���=%FrAl�)�Q���T�3�oƂ�!��*:I�S�]�z���\�7��1��%�8����Y)٩�q_�?8B}�@�F��>/{B�XC�J����M�Ȝ ����K�6��}So+�^� ��,5b|W�pP��9��)�r�qV'�kO���x��鉦�}mq�mB۠p��΀/$V4]#a*3C;��)$x��l�y��֜���2N���+M����]�oGo��HvD�$d���b��9.�y��ϰ6���m�9`�F V�2��{uG~F����;��ڢ�.�ޢ32��\�L�#���-���#ׁ��j�)7T���b�$�g�;l)�*�C�p���R[��#-1X���B���������-�&��"�L��Tv�;�{��ֵ�� ����oݯ͆"��~p1L���~I��Ի����*�ds��&Yv3��u�_��x0����	�Z���Cg4�����RT��fj�������G él ��7�G%�Eпޞu�:����c�ɸ�I�d��%|�T#���@� /tBeE��Xd`�)�\�my�c=�_D�Z����_���k3��2�ܕ;k^y�0�v�u�H<�C��~���\����O�TN��4�%����w���D~��U�}�Zo���F���vs�-��`	{�{i�[��9�٩C�~��R14:pU��(�c�)9�����s�2J]bf�ކS��/F�ڷ7�z;�1[4VY�'�d4hN�'��;������a4�q�"=�[�e���x`0s�$�H�����C���DL�Tה[�^���2��؎��&��ex8 �qQ��SSET_CAMERA.rotateY(Math.abs(degrees));
        OFFSET_CAMERA.getMatrix(OFFSET_MATRIX);
        OFFSET_CAMERA.restore();

        OFFSET_MATRIX.preTranslate(-width * 0.5f, -height * 0.5f);
        OFFSET_MATRIX.postTranslate(width * 0.5f, height * 0.5f);
        OFFSET_TEMP_FLOAT[0] = width;
        OFFSET_TEMP_FLOAT[1] = height;
        OFFSET_MATRIX.mapPoints(OFFSET_TEMP_FLOAT);
        return (width - OFFSET_TEMP_FLOAT[0]) * (degrees > 0.0f ? 1.0f : -1.0f);
    }
}
