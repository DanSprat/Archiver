# Archiver
***Package PriorityHuffmanTree***<br />
- **Class Node** - звено дерева (содержит вес, значение, булевую переменную, которая указывае есть ли еще потомки, а также ссылки на самих потомков(если они есть)
- **Class HuffmanTree** - собственно само дерево, которое строится на основе приоритетной очереди
- **Class Archiver** - собственно главный класс, в котором реализованы методы архивирования и разархивирования. 
  - Статический метод ***compress*** (String path,String outDir,String nameZIP)<br/>
    **path** - путь до содержимого,которое нужно заархивировать (файл или папка)<br/>
    **outDir** - путь создания архива<br/>
    **nameZIP** - название архива<br/>
  - Статический метод ***extract*** (String stringArchivePath,String stringOutDirPath)<br/>
    **stringArchivePath** - путь архива<br/>
    **stringOutDirPath** - путь места,куда разархивировать<br/>

