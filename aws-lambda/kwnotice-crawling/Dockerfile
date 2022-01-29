FROM public.ecr.aws/lambda/python@sha256:b15110cfc524c410f9c3b3e906b4fa2fe268c38811c4c34f048e8e5d4c9669c8 as build

RUN yum install -y unzip && \
    curl -Lo "/tmp/chromedriver.zip" "https://chromedriver.storage.googleapis.com/97.0.4692.71/chromedriver_linux64.zip" && \
    curl -Lo "/tmp/chrome-linux.zip" "https://www.googleapis.com/download/storage/v1/b/chromium-browser-snapshots/o/Linux_x64%2F938549%2Fchrome-linux.zip?alt=media" && \
    unzip /tmp/chromedriver.zip -d /opt/ && \
    unzip /tmp/chrome-linux.zip -d /opt/


FROM public.ecr.aws/lambda/python@sha256:b15110cfc524c410f9c3b3e906b4fa2fe268c38811c4c34f048e8e5d4c9669c8

RUN yum install atk cups-libs gtk3 libXcomposite alsa-lib \
    libXcursor libXdamage libXext libXi libXrandr libXScrnSaver \
    libXtst pango at-spi2-atk libXt xorg-x11-server-Xvfb \
    xorg-x11-xauth dbus-glib dbus-glib-devel -y

RUN pip install --upgrade pip

RUN pip install requests
RUN pip install beautifulsoup4
RUN pip install pymysql
RUN pip install selenium
RUN pip install firebase_admin

COPY --from=build /opt/chrome-linux /opt/chrome

COPY --from=build /opt/chromedriver /opt/

COPY *.py ./
COPY *.json ./

CMD [ "app.handler" ]