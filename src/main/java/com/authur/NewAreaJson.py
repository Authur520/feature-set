import requests

mainCode = 100000
mainName = '中华人民共和国'

def getList(rootCode, rootName, lv):
    myData = {
        'code': str(rootCode),
        'label': rootName,
        'value': rootName,
    }
    api = 'https://geo.datav.aliyun.com/areas/bound/geojson?code={}_full'.format(rootCode)
    data = requests.get(api)
    if data.json():
        arr = []
        index = 1
        for i in data.json().get('features'):
            item = i.get('properties')
            mycode = item.get('adcode')
            myname = item.get('name')
            print('{}>  {}'.format('|' + '=========' * lv, myname))
            childrenNum = item.get('childrenNum')
            if not mycode == rootCode and mycode and myname and not childrenNum == 0:
                thisData = getList(mycode, myname, lv + 1)
                arr = [*arr, thisData]
            else:
                arr = [*arr, {
                    "code": str(mycode),
                    "label": myname,
                    "value": myname,
                }]
        if not len(arr) == 0:
            myData['children'] = arr
        return myData
    else:
        return {}


def main():
    arr = getList(mainCode, mainName, 1)
    arrString = arr.__repr__().replace('\'', '\"')
    with open('./省市区.json', 'w') as file:
        file.write(arrString)
        file.close()


if __name__ == '__main__':
    main()
