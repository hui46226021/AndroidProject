�}G* 
 �  T�K�7GȢ�9&	��d+�1w;c���*������+b��QR'V߰�������)F�e��_#��*d�x ]�gC�ۮ�5�#�����ZP�L�|���������|��E�۝y,����~�<rзyo��⏵C0Βk�`�g�2��_ ��^�����qwF����s���$��vS�n�	 ��tbS�ꧭӭ��b{}"����=@���$o0�w	��M-���t��XZ:�E���QC���e)H%&�r�?�4�^`������l �+��2@>�\F�fE���<������D�O�oU���{%�Ry�&'e�k�6^��j���Ҭrm	��ϭ6v��~=���\���%4 Vc������2N���+M����]�oGo��HvD�$d���b��9.�y��ϰ6���m�9`�F V�2��{uG~F����;��ڢ�.�ޢ32��\�L�#���-���#ׁ��j�)7T���b�$�g�;��为key缓存起来  通过对 实体对象反射 赋值 或读取 操作
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormInjection {
    String name();
    boolean isNull() default false;
    String message() default "参数";
}
