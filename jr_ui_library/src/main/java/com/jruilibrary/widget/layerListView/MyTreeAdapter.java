�},* 
 �  t�go;X    ��+�1��bܤ�w��>X8E�+���Z�'�>��^j�J&�.�ׂ ��R⟧0/�SRB� ��#H�{��v����O�e9�O�ϖ8e���c�9=O�'Ɉ�\2t����^{(/��F֍�H��.��<�s'��l���(q�X�h��,�\Iە�}�J�y|5��F�>a���+�՞Mf2Ϻcǈ��5}���T"�k�k�ud>�q���~�Nhy��Df�ķ����F������U�m4;!���߷�~��Q�����?R�N�B)\�@�F¦����j>t�T�x��鉦�}mq�mB۠p��΀/$V4]#a*3C;��)$x��l�y��֜���2N���+M����]�oGo��HvD�$d���b��9.�y��ϰ6���m�9`�F V�2��{uG~F����;��ڢ�.�ޢ32��\�L�#���-���#ׁ��j�)7T���b�$�g�;l�*��D�qL��%�HF��P48�ʒ�+>����#����G쑍���o�F`u �x���&��j��&N��$�����0~kl��CP�����W�r��&'m��x�oM7C����u5�:�%a�[LfT=A�bj	D���,+��w/������:j�1�`x�
�]�!����	y���)-hĎǷA����!�#Ma�{h���:������Q4�[XRy�Ny����G��Yj��_���E`��=���ɬ�/(���wF��rWE0��m�@e�:��m{R%/@kA1]�|z�Z8�h����F�Mo3�[�;�_�%�1�d3 �}�4G򼌲Ԁ<Og^���b5l���v5:T�������\�����O����j����tئ���oJ`#qUDMT����1�S�Y%Ef�x�Ik'��E��B��Ey�=��/�&e�o�Kl�`ej81�<�d�R2��#h&����b1�����s�R��j�U��׮xJ})>l�!�J����NR���z��5����R���z�
�L��}��aC�dg��{E�__�0�B��T��Y�	M�2�5n�]�L�Po���@�l���X���)�`�B���9
D����Z
_��'�`L�x�Qk�>�;��2��FZ�Q���wPo<)�"ۄ��%7@ 8]��L*>��HfTJ�� �Ij��(-�zY�FFA����K�6N>��U=���9����QE>� Z�n٣����kǰ&��[~A�����8�侗=S�d�����<&�����E�[�n׫w�\��j��7!֍RZ3�|�с`$���v�H�fS�3�.�VQ�>n,�C	J��l�>���?�"�`(�yc�Y��ÀuS�������D���Y�v�&S'-E�vs�����S�"����&��'�Ee>�"E��Ӯ��*O�x�6׶�K�9���=1 �_{۠��i1\���C�⠢�+���0��糋�St�i&H%k3�;�\!��g�ڿy;T����l$R�&a��צˌ,�p�M�ǽ�q=���q��L���-r	�t˘R@pĞBQ"ӝ�I8>H��]����̂��&x#�*+F�xeo�\�y��f
a8q z���&������8~Jg�Z��u��B��@*�_����A[� ������*�y~؋�*��ϓm����)n���/�������cm�zv��[��i�U�ӛ�)q΄[�[=�FVf��:�Ea���0��~j�R���`]-؂��� ���Pչ��6�.G��z4.�"� PD��A�?��ͼ�w[H��귵����t������̓ہLX��.�1oo����x�Ýq�u�2�>k\���ۥ
f?�?����H�E*߸G$���{�%8�L8N�.4����I���ڈ�zu%л��]�V<a�>��+�ۿ �s��^�j��˖y�zJ�?UK��T���5#)��k�=�?i�i��[]E�GL�{�g�_~��:�@Ad����1�g�	6�ӱ�:���L/=$K��U�P�tImageResource(R.drawable.yjt2);
			viewHolder.label.setTextColor(mContext.getResources().getColorStateList(R.color.text_gray1));
		}

		if(node.isLeaf()){
			viewHolder.openIcon.setVisibility(View.INVISIBLE);
		}else {
			viewHolder.openIcon.setVisibility(View.VISIBLE);
		}


		viewHolder.label.setText(node.getName());
		
		
		return convertView;
	}

	private final class ViewHolder
	{
		ImageView icon;
		TextView label;
		ImageView openIcon;
	}

}
